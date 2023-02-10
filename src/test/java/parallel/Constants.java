package parallel;

public class Constants {
	
	private static ThreadLocal<com.data.Arom_Constants> API_Constants = new ThreadLocal<com.data.Arom_Constants>();
	
	public static void SetAPIConstants(com.data.Arom_Constants obj) {
		API_Constants.set(obj);
	}
	
	public static com.data.Arom_Constants getAPIConstants() {
		return API_Constants.get();
	}
	
	public static void RemoveAPIConstants() {
		API_Constants.remove();
	}

}
