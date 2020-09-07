package com.example.baselibrary.di.modules;

import android.database.sqlite.SQLiteDatabase;
import com.example.baselibrary.application.MyApplication;
import com.example.baselibrary.constants.ConfigConstants;
import com.example.baselibrary.data.database.MyDaoMaster;
import com.example.baselibrary.utils.LogUtils;
import com.example.kotlinmvpdemo.DaoMaster;
import com.example.kotlinmvpdemo.DaoSession;
import dagger.Module;
import dagger.Provides;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import javax.inject.Singleton;
import java.util.List;

/**
 * 数据库辅助类,简单的写法 7
 */
@Module
public class DBHelperModule {


    //    private DaoMaster.DevOpenHelper mOpenHelper;
//    private DaoMaster mDaoMaster;
//    private DaoSession mDaoSession;

    @Singleton
    @Provides
    public DBHelperModule providegetDBHelperModule() {
        return new DBHelperModule();
    }


    /**
     * 获取 DaoMaster.DevOpenHelper 实例
     *
     * @return
     */
    @Singleton
    @Provides
    public MyDaoMaster providegetDevOpenHelper() {
        return new MyDaoMaster(MyApplication.myapplication.getApplicationContext(),
                ConfigConstants.DB_SQL_NAME, null);
    }

    /**
     * 获取 DaoMaster 实例
     *
     * @param mOpenHelper
     * @return
     */
    @Singleton
    @Provides
    public DaoMaster providegetDaoMaster(MyDaoMaster mOpenHelper) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        return new DaoMaster(db);
    }

    @Singleton
    @Provides
    public DaoSession providegetDaoSession(DaoMaster mDaoMaster) {
        return mDaoMaster.newSession();
    }

    /**
     * 输出日志
     */
    public void setDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    /**
     * 关闭Helper
     */
    public void closeHelper(MyDaoMaster mOpenHelper) {
        if (mOpenHelper != null) {
            mOpenHelper.close();
            mOpenHelper = null;
        }
    }

    /**
     * 关闭session
     */
    public void closeSession(DaoSession mDaoSession) {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    public <T> T getDao(Class<? extends Object> entityClass, DaoSession daoSession) {
        return (T) daoSession.getDao(entityClass);
    }

    /**
     * 插入数据
     */
    public boolean insert(Object object, DaoSession mDaoSession) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls, mDaoSession)).insertInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls, mDaoSession)).insert(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 插入或者修改
     */
    public boolean insertOrReplace(Object object, DaoSession mDaoSession) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls, mDaoSession)).insertOrReplaceInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls, mDaoSession)).insertOrReplace(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除单条数据
     */
    public boolean delete(Object object, DaoSession mDaoSession) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls, mDaoSession)).deleteInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls, mDaoSession)).delete(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除单条数据，根据主键的值
     */
    public boolean deleteByKey(Object object, long key, DaoSession mDaoSession) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, Long>) getDao(cls, mDaoSession)).deleteByKeyInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, Long>) getDao(cls, mDaoSession)).deleteByKey(key);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 删除全局数据，清空表
     */
    public boolean deleteAll(Class<? extends Object> classType, DaoSession mDaoSession) {
        try {
            ((AbstractDao<Object, String>) getDao(classType, mDaoSession)).deleteAll();
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 修改单条数据
     */
    public boolean update(Object object, DaoSession mDaoSession) {
        try {
            Class cls;
            if (object instanceof List) {
                List listObject = (List) object;
                if (listObject.isEmpty()) {
                    LogUtils.e("listObject is null!");
                    return false;
                }
                cls = listObject.get(0).getClass();
                ((AbstractDao<Object, String>) getDao(cls, mDaoSession)).updateInTx(listObject);
            } else {
                cls = object.getClass();
                ((AbstractDao<Object, String>) getDao(cls, mDaoSession)).update(object);
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 查询单条数据，根据主键的值
     */
    public <T> T selectByPrimaryKey(Class<T> classType, long key, DaoSession mDaoSession) {
        return ((AbstractDao<T, Object>) getDao(classType, mDaoSession)).load(key);
    }

    /**
     * 查询整表数据
     */
    public <T> List<T> loadAll(Class<T> classType, DaoSession mDaoSession) {
        return ((AbstractDao<T, Object>) getDao(classType, mDaoSession)).loadAll();
    }

    /**
     * 自定义查询
     */
    public <T> List<T> getQueryRaw(Class<T> classType, DaoSession mDaoSession, String where, String... selectionArg) {
        return ((AbstractDao<T, Object>) getDao(classType, mDaoSession)).queryRaw(where, selectionArg);
    }

    /**
     * QueryBuilder 查询
     */
    public <T> QueryBuilder<T> getQueryBuilder(Class<T> classType, DaoSession mDaoSession) {
        return ((AbstractDao<T, Object>) getDao(classType, mDaoSession)).queryBuilder();
    }
}
