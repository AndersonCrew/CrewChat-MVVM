package com.crewcloud.apps.crewchat.data.di

import android.content.Context
import androidx.room.Room
import com.crewcloud.apps.crewchat.data.local.AppPreferenceDataStore
import com.crewcloud.apps.crewchat.data.local.AppPreferenceDataStoreImpl
import com.crewcloud.apps.crewchat.data.local.CrewChatDatabase
import com.crewcloud.apps.crewchat.data.local.dao.ChattingDao
import com.crewcloud.apps.crewchat.data.local.dao.DepartmentDao
import com.crewcloud.apps.crewchat.data.local.dao.EmployeeDao
import com.crewcloud.apps.crewchat.data.local.dao.UserDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by BM Anderson on 2/7/26.
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): CrewChatDatabase {
        return Room.databaseBuilder(context, CrewChatDatabase::class.java, "CrewChat-Room-DB")
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: CrewChatDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideDepartmentDao(database: CrewChatDatabase): DepartmentDao {
        return database.departmentDao()
    }

    @Provides
    @Singleton
    fun provideEmployeeDao(database: CrewChatDatabase): EmployeeDao {
        return database.employeeDao()
    }

    @Provides
    @Singleton
    fun provideChattingDao(database: CrewChatDatabase): ChattingDao {
        return database.chattingDao()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface AppDataStore {

    @Binds
    @Singleton
    abstract fun provideAppPreferenceDataStore(
        impl: AppPreferenceDataStoreImpl
    ): AppPreferenceDataStore
}

/*1. User thay đổi thì thang HomeScreen bị recompose , đọc tới Header , Footer ko thay sử dụng state nao nên dc skip, thang UserList đang đọc user nên bị recompose
* 2. vì khi chạy lần đầu hoặc activity bị recreate lại , hoặc composable bị remove rồi add lại cây
* 3. thì thì cả 100 thang bị recompose vì ko có key , nó ko biêt thăng nào mới đổi
* 4. nên tạo remmmẻbr, vì môi lần recompoe nó se tạo 1 SimpleDateFromat instance, có remmember đê rgiam allowcation
* 5. nó còn tốn thêm memory để lưu bundle ( à chỗ này e quên mất la remmermberSaveAble có sông qua process death ko?
* 6. có, vì SnapShotState đang giữ object value của state , nên khi state thay đổi nó đánh dấu cả 3 thăng đang đều đọc value đó , nên nó sẽ recompose
* 7. rememberUpdatedState khi muốn update callback trong 1 LaunchEffect mà ko muốn chạy lại LaucnhEffect , ví dụ ta truyền vô 1 composale 1 call back onSuccess là sẽ làm gì đó , và ta gọi onSuccess đó ở LaucnhEfffect, nhưng đang trong qua trình chạy thi thăng cha
* thay đổi nội dung cua onSuccess đó , thi thăng onSuccess mới sẽ được sử dụng ơ LaunchEffect cua thang con mà ko cần chạy lại LE
* 8. chưa biet*/

