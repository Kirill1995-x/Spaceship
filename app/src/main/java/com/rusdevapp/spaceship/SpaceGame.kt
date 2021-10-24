package com.rusdevapp.spaceship

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.view.MotionEvent
import android.view.SurfaceView
import androidx.fragment.app.FragmentManager
import com.rusdevapp.spaceship.elements.Asteroid
import com.rusdevapp.spaceship.elements.Background
import com.rusdevapp.spaceship.elements.Spaceship
import java.util.*

class SpaceGame(context: Context): SurfaceView(context), Runnable
{
    private var app: App //класс с константами
    private lateinit var activity: SpaceActivity
    private lateinit var supportFragmentManager: FragmentManager
    private var thread: Thread //класс для создания потока
    private var paint: Paint //класс для рисования объектов
    private var random: Random //класс для генерации рандомных значений
    private var sharedPreferences: SharedPreferences
    private var soundPool: SoundPool
    private var sound: Int = 0
    //Флаги
    private var continuePlay: Boolean = false //флаг продолжения работы
    private var gameOver: Boolean = false //флаг окончания игры
    private var changePositionOfSpaceship: Boolean = false //флаг перемещения корабля
    private var score: Int = 0 //кол-во километров
    //Элементы
    private var background1: Background
    private var background2: Background
    private var spaceship: Spaceship
    private var asteroids: Array<Asteroid>

    init {
        thread = Thread(this)
        app = App(context)
        sharedPreferences = this.context.getSharedPreferences(app.APP, MODE_PRIVATE)
        random = Random()
        paint = Paint()
        //---
        soundPool = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val audioAttributes: AudioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()
            SoundPool.Builder().setAudioAttributes(audioAttributes).build()
        }
        else SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        sound = soundPool.load(context, R.raw.death, 1)
        //---
        background1 = Background(app.getWidthOfScreen(), app.getHeightOfScreen(),
                                 resources, R.drawable.background1)
        background2 = Background(app.getWidthOfScreen(), app.getHeightOfScreen(),
                                 resources, R.drawable.background2)
        background1.shift_x = 0f
        background2.shift_x = app.getWidthOfScreen().toFloat()
        spaceship = Spaceship(app,
                              sharedPreferences.getInt("spaceship", R.drawable.spaceship1),
                              resources)
        asteroids = arrayOf(Asteroid(app, resources, R.drawable.asteroid1),
                            Asteroid(app, resources, R.drawable.asteroid2),
                            Asteroid(app, resources, R.drawable.asteroid3),
                            Asteroid(app, resources, R.drawable.asteroid4))
    }

    constructor(context: Context, activity: SpaceActivity, supportFragmentManager: FragmentManager)
               : this(context)
    {
        this.activity = activity
        this.supportFragmentManager = supportFragmentManager
    }

    fun resume():Unit
    {
        continuePlay = true
        thread.start()
    }

    fun pause():Unit
    {
        continuePlay = false
        thread.join()
    }

    override fun run() {
        while(continuePlay)
        {
            update() // обновление экрана
            draw() //отрисовка экрана
            Thread.sleep(app.TIME_FOR_SLEEP) //пауза
        }
    }

    private fun update() {
        //настройка фона
        background1.shift_x -= 10f
        background2.shift_x -= 10f
        if(background1.shift_x+background1.getWidthOfBackground()<0)
            background1.shift_x = app.getWidthOfScreen().toFloat()
        if(background2.shift_x+background2.getWidthOfBackground()<0)
            background2.shift_x = app.getWidthOfScreen().toFloat()
        //настройка отображения корабля
        if(changePositionOfSpaceship) spaceship.shift_y -= 30 else spaceship.shift_y += 30
        if(spaceship.shift_y<0) spaceship.shift_y=0
        if(spaceship.shift_y>=app.getHeightOfScreen()-spaceship.height)
            spaceship.shift_y=app.getHeightOfScreen()-spaceship.height
        //настройка отображения астероидов
        asteroids.forEach { asteroid ->
            asteroid.shift_x -= asteroid.step_of_shift_x
            if(asteroid.shift_x+asteroid.getWidthOfAsteroid()<0)
            {
                //при достижении каждой 1000 км скорость астероидов будет увеличиваться
                asteroid.step_of_shift_x=(random.nextInt(25)+10+score/1000)
                asteroid.shift_x = app.getWidthOfScreen()//астероиды стартуют справа
                asteroid.shift_y = random.nextInt(app.getHeightOfScreen()-
                        asteroid.getHeightOfAsteroid())//рандомная позиция по y
            }
        //проверка на столкновение обновление кол-ва километров
            if(Rect.intersects(asteroid.getShapeForCheckCollision(),
                               spaceship.getShapeForCheckCollision()))
            {
                if(sharedPreferences.getBoolean("volume", true))
                {
                  soundPool.play(sound, app.leftVolume, app.rightVolume, 0, 0, app.rate)
                }
                gameOver=true
            }
            else score++
        }
    }

    private fun draw() {
        if(holder.surface.isValid)
        {
            val canvas:Canvas = holder.lockCanvas()//заблокировать холст
            canvas.drawColor(Color.BLACK)
            canvas.drawBitmap(background1.getBackground(), background1.shift_x,
                              background1.shift_y, paint)
            canvas.drawBitmap(background2.getBackground(), background2.shift_x,
                              background2.shift_y, paint)
            asteroids.forEach { asteroid ->
                canvas.drawBitmap(asteroid.getAsteroid(), asteroid.shift_x.toFloat(),
                                  asteroid.shift_y.toFloat(), paint)
            }
            //при достижении очередной тысячи с 0 по 50 км текст и размер текста будет меняться
            if(score%1000<=50)
            {
                paint.textSize = app.NEXTLEVEL_TEXTSIZE
                paint.color = app.NEXTLEVEL_COLOR
            }
            else
            {
                paint.textSize = app.DEFAULT_TEXTSIZE
                paint.color = app.DEFAULT_COLOR
            }
            canvas.drawText(score.toString(), app.getWidthOfScreen()*app.placeForTextX,
                app.getHeightOfScreen()*app.placeForTextY, paint)
            //---
            if(gameOver)
            {
                continuePlay = false//остановка обновлений
                holder.unlockCanvasAndPost(canvas) //разблокировать холст
                //---
                if(sharedPreferences.getInt("score",0)<score)
                    sharedPreferences.edit().putInt("score",score).apply()
                val dialogGameOver:DialogGameOver = DialogGameOver()
                dialogGameOver.isCancelable=false
                dialogGameOver.show(supportFragmentManager, "GameOverDialog")
            }
            else
            {
                canvas.drawBitmap(spaceship.getSpaceship(),
                                  spaceship.shift_x.toFloat(),
                                  spaceship.shift_y.toFloat(),
                                  paint)
                holder.unlockCanvasAndPost(canvas) //разблокировать холст
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action)
        {
            MotionEvent.ACTION_DOWN -> changePositionOfSpaceship=true //нажатие на экран
            MotionEvent.ACTION_UP -> changePositionOfSpaceship=false //отпускание экрана
        }
        return true
    }
}