package base;

import java.lang.reflect.Array;

public class Utilities {
	
	private static Utilities instance;
	
	public static Utilities getInstance() {
		if (Utilities.instance == null) {
			Utilities.instance = new Utilities();
		}
		
		return Utilities.instance;
	}
	
	public <T> T[] concatenate(Class<T> type, T object0, T object1) {
		T array0[] = (T[]) Array.newInstance(type, 1);
		array0[0] = object0;
		T array1[] = (T[]) Array.newInstance(type, 1);
		array1[0] = object1;
		
		return this.concatenate(type, array0, array1);
	}
	
	public <T> T[] concatenate(Class<T> type, T object, T ...objects) {
		T array[] = (T[]) Array.newInstance(type, 1);
		array[0] = object;
		return this.concatenate(type, array, objects);
	}
	
	public <T> T[] concatenate(Class<T> type, T array0[], T ...array1) {
		T array[] = (T[]) Array.newInstance(type, array0.length + array1.length);
		
		for (int i = 0; i < array0.length; i++) {
			array[i] = array0[i];
		}
		
		for (int i = 0; i < array1.length; i++) {
			array[i + array0.length] = array1[i];
		}
		
		return array;
	 }
}
