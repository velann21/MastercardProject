package apk.bookmyShow.regsuite;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import apk.bookmyShow.Config.TestConfig;
import apk.bookmyShow.pomAction.CityHomePageAction;
import apk.bookmyShow.pomAction.LoginPageAction;
import apk.bookmyShow.pomAction.MainPageAction;
import apk.bookmyShow.pomAction.PickRegionAction;
@Listeners(apk.bookmyShow.helper.IlistnersIntializer.class)
public class Test_Sc_01 extends TestConfig {
	@Test()
	public void tc_01()
	{
		ArrayList userNameList = null;
		ArrayList passwordList = null;
		ArrayList region = null;
		try {
			userNameList = getters.fromExcelValueExtractor(excelPath, "ConfigSheet", "Test_Sc_01", "InputSheet",
					getters, 1);
			// System.out.println("userNameList" + userNameList);
			passwordList = getters.fromExcelValueExtractor(excelPath, "ConfigSheet", "Test_Sc_01", "InputSheet",
					getters, 2);
			// System.out.println("passwordList" + passwordList);
			region = getters.fromExcelValueExtractor(excelPath, "ConfigSheet", "Test_Sc_01", "InputSheet", getters, 3);
			// System.out.println("region" + region);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		MainPageAction mainPageActionInstance = MainPageAction.MainPageActionInstance(driver, handle);
		LoginPageAction loginpageInstance2 = LoginPageAction.loginpageInstance(driver, handle);
		PickRegionAction pick = PickRegionAction.PickRegionActionInstance(driver, handle);
		CityHomePageAction hp = CityHomePageAction.CityHomePageActionInstance(driver, handle);
		for (int i = 0; i <= userNameList.size(); i++) {
			try {
				mainPageActionInstance.clickLoginButton();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			loginpageInstance2.login((String) userNameList.get(i), (String) passwordList.get(i));
			pick.searchCity_Scroll((String) region.get(i));
			hp.pageRoller();
			driver.resetApp();
		}
	}
}
