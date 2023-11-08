package net.juxyc.guilds.utils.reflection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;

public class ReflectionHelper {

    private static String NMS_PREFIX = "net.minecraft.server.unknown";
    private static String OCB_PREFIX = "org.bukkit.craftbukkit.unknown";

    private static Class<?> CraftPlayerClass;
    private static Class<?> EntityPlayerClass;
    private static Method getHandle;

    public static void setNmsPrefix(String nmsPrefix) {
        NMS_PREFIX = nmsPrefix;
    }

    public static void setOcbPrefix(String ocbPrefix) {
        OCB_PREFIX = ocbPrefix;
    }

    public void initialize(){
        setOcbPrefix("org.bukkit.craftbukkit." + getVersion() + ".");
        setNmsPrefix("net.minecraft.server." + getVersion() + ".");
        CraftPlayerClass = getOcbClass("entity.CraftPlayer");
        EntityPlayerClass = getNmsClass("EntityPlayer");
        getHandle = getMethod(EntityPlayerClass, "getHandle");
    }

    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(23);
    }

    public static Object getHandle(Player player){
        try {
            return getHandle.invoke(player);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getPlayerConnection(Player player){
        return getFieldValue(getHandle(player), "playerConnection");
    }

    public static Object as(Object from, Class<?> to){
        return to.cast(from);
    }

    public static Class<?> getOcbClass(String name){
        try {
            return Class.forName(OCB_PREFIX + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> getNmsClass(String name){
        try {
            return Class.forName(NMS_PREFIX + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String methodName){
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            declaredMethod.setAccessible(true);
            if(declaredMethod.getName().equals(methodName)) return declaredMethod;
        }
        return null;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... params){
        for (Method declaredMethod : clazz.getDeclaredMethods()) {
            declaredMethod.setAccessible(true);
            if (declaredMethod.getName().equals(methodName) && Arrays.equals(declaredMethod.getParameterTypes(), params))
                return declaredMethod;
        }
        return null;
    }

    public static Field getField(Class<?> clazz, String fieldName){
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            if(declaredField.getName().equals(fieldName))
                return declaredField;
        }
        return null;
    }

    public static <T> T getValue(Class<?> clazz, String fieldName, Class<T> target, Object object){
        Field field = getField(clazz, fieldName, target);
        if(field != null){
            try {
                return (T) field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Field getField(Class<?> clazz, String fieldName, Class<?> fieldType){
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            if(declaredField.getName().equals(fieldName) && declaredField.getType().equals(fieldType))
                return declaredField;
        }
        return null;
    }

    public static void setFieldValue(Object target, String fieldName, Object value){
        Class<?> clazz = target.getClass();
        Field field = getField(clazz, fieldName);
        if(field == null || !field.getType().equals(value.getClass())) return;
        try {
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        field.setAccessible(false);
    }

    public static Object getFieldValue(Class<?> clazz, String fieldName){
        Field field = getField(clazz, fieldName);
        if(field == null) return null;
        try {
            return field.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getFieldValue(Object object, String fieldName){
        Field field = getField(object.getClass(), fieldName);
        if(field == null) return null;
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invoke(Field field, Object object){
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object invoke(Method method, Object object, Object... params){
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T newInstance(Constructor<T> constructor, Object... params){
        try {
            return constructor.newInstance(params);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Function<String, ? extends T> newFunctionInstance(Constructor<T> constructor, Object... params){
        return (Function<String, T>) s -> newInstance(constructor, params);
    }

    public static <T> T newInstance(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... params){
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            constructor.setAccessible(true);
            if(Arrays.equals(constructor.getParameterTypes(), params))
                return (Constructor<T>) constructor;
        }
        return null;
    }
}