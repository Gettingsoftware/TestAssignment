package ebay.qa.testautomation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;

import com.google.common.collect.Ordering;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import utilities.WebConnector;


public class EbayHomePageStepDefinition extends WebConnector {
	
	/**
	 * Background
	 * 
	 */
	
	@Given("^I am a non-registered customer$")
	public void i_am_a_non_registered_customer() throws Throwable {
	}

	@Given("^I navigate to www\\.ebay\\.co\\.uk$")
	public void i_navigate_to_www_ebay_co_uk() throws Throwable {
		driver.get(baseurl);
	    Thread.sleep(2000);
	}

	/**
	 * @Scenario001
	 * 
	 */
	@When("^I search for an item \"([^\"]*)\"$")
	public void i_search_for_an_item(String arg1) throws Throwable {
		driver.findElement(By.xpath("//input[@id='gh-ac']" )).sendKeys(arg1);
	    driver.findElement(By.xpath("//input[@id='gh-btn']")).submit();
	    Thread.sleep(2000);
	}
	
	@Then("^I get a list of \"([^\"]*)\" matching results$")
	public void i_get_a_list_of_matching_results(String arg1) throws Throwable {
		String text = driver.findElement(By.xpath("//h1[@class='rsHdr']")).getText();
		Thread.sleep(2000);
	    Assert.assertTrue(text.contains(arg1));
	}

	@Then("^the resulting items cards show: postage price, No of bids, price or show BuyItNow tag$")
	public void the_resulting_items_cards_show_postage_price_No_of_bids_price_or_show_BuyItNow_tag() throws Throwable {
		int listViewCount = driver.findElements(By.xpath("//ul[@id='ListViewInner']/li")).size();
		
		if (listViewCount !=0) {
			for (int i=1; i<=listViewCount; i++) {
				driver.findElement(By.xpath("//ul[@id='ListViewInner']/li[" + i + "]" + "/ul[@class='lvprices left space-zero']")).isDisplayed();
			}
			}
			}

	@Then("^I can sort the results by Lowest Price$")
	public void i_can_sort_the_results_by_Lowest_Price() throws Throwable {
		WebElement getmenu= driver.findElement(By.xpath("//ul[@class='sel']/li[@class='dropdown small']/div[@class='sort-menu-container']"));
	    Actions act = new Actions(driver);
	    act.moveToElement(getmenu).perform();
	    Thread.sleep(2000);
	    
	    driver.findElement(By.id("SortMenu")).click();
		
	}

	@Then("^the results are listed in the page in the correct order$")
	public void the_results_are_listed_in_the_page_in_the_correct_order() throws Throwable {
		int itemSize = driver.findElements(By.xpath("//ul[@id='ListViewInner']/li")).size();
		String sortValue = driver.findElement(By.xpath("//ul[@class='sel']/li[@class='dropdown small']/div[@class='sort-menu-container']/a")).getText();
		String value = "";
		
		// This list will iterate to the price Xpath value of all the sorted items on the page
		// and add them to the list one by one.
		List<Double> itemsPrice = new ArrayList<Double>();
		for (int i = 1; i<=itemSize; i++) {
			
			// This if check is put in here because the ul class of the price for 
			// the first value sorted by Highest price has a different value compared to others items
			// So this check is specifically put in here for the first element with highest price to be added in the list
			if ((i == 1 || i == 21 || i == 47) && sortValue.indexOf("Highest price") >= 0) {
				value = driver.findElement(By.xpath("//ul[@id='ListViewInner']/li[" + i + "]/ul[@class='lvprices left space-zero conprices']/li[1]/span")).getText().substring(1);
				value = value.replace(",", "");
				itemsPrice.add(Double.parseDouble(value));
			} else {
				value = driver.findElement(By.xpath("//ul[@id='ListViewInner']/li[" + i + "]/ul[@class='lvprices left space-zero']/li[1]/span")).getText().substring(1);
				value = value.replace(",", "");
				itemsPrice.add(Double.parseDouble(value));
			}
		}
		
		// Once the list containing the prices of all items on page is available
		// Guava provides this functionality through its awesome Ordering class
		// Where a list will be directly passed on to the method and it returns true or false if list is sorted
		// If check is implement to check the ascending order for sort based on lowest price
		// And to check descending order for sort based on highest price
		
		if (sortValue.indexOf("Highest price") >= 0) {
			boolean sortedByHighestPrice = Ordering.natural().reverse().isOrdered(itemsPrice);
			Assert.assertTrue(sortedByHighestPrice);
		} else {
			boolean sortedByLowestPrice = Ordering.natural().isOrdered(itemsPrice);
			Assert.assertTrue(sortedByLowestPrice);
		}

	}

	@Then("^I can sort the results by Highest Price$")
	public void i_can_sort_the_results_by_Highest_Price() throws Throwable {
		WebElement getmenu= driver.findElement(By.xpath("//ul[@class='sel']/li[@class='dropdown small']/div[@class='sort-menu-container']"));
	    Actions act = new Actions(driver);
	    act.moveToElement(getmenu).perform();
	    Thread.sleep(2000);
	    
	    driver.findElement(By.xpath("//ul[@id='SortMenu']/li[4]")).click();
	}

	@Then("^I can filter the results by 'Buy it now'$")
	public void i_can_filter_the_results_by_Buy_it_now() throws Throwable {
	    driver.findElement(By.xpath("//a[@class='small cbx btn btn-s btn-ter tab tgl_button last_b']")).click();
	}

	@Then("^all the results shown in the page have the 'Buy it now' tag$")
	public void all_the_results_shown_in_the_page_have_the_Buy_it_now_tag() throws Throwable {
	    driver.findElement(By.xpath("//span[@class='small sel tgl_button last_b']")).isSelected();
	}
	

	/**
	 * @Scenario002
	 * 
	 */
	@When("^I enter a search term and select a specific Category$")
	public void i_enter_a_search_term_and_select_a_specific_Category() throws Throwable {
		driver.findElement(By.xpath("//button[@id='gh-shop-a']")).click();
		   Thread.sleep(2000);
		driver.findElement(By.xpath("//table[@id='gh-sbc']/tbody/tr/td[2]/ul[1]/li[4]")).click();
		   Thread.sleep(2000);
	    driver.findElement(By.xpath("//input[@id='gh-ac']")).sendKeys("desk");
	    driver.findElement(By.id("gh-btn")).submit();
		  Thread.sleep(2000);
	}

	@Then("^I get a list of matching results$")
	public void i_get_a_list_of_matching_results() throws Throwable {
		String text = driver.findElement(By.xpath("//h1[@class='rsHdr']")).getText();
		Assert.assertTrue(text.contains("desk"));
	}

	@Then("^I can verify that the results shown as per the the selected category$")
	public void i_can_verify_that_the_results_shown_as_per_the_the_selected_category() throws Throwable {
		String text = driver.findElement(By.xpath("//div[@class='rlp-b']/div[2]")).getText();
		Assert.assertTrue(text.contains("Home, Furniture & DIY"));
	    
	}
	
	/**
	 * @Scenario001
	 * 
	 */
	
	@When("^I search for an item$")
	public void i_search_for_an_item() throws Throwable {
	    driver.findElement(By.xpath("//input[@id='gh-ac']")).sendKeys("toys");
	    driver.findElement(By.xpath("//input[@id='gh-btn']")).click();
	    Thread.sleep(1000);
	}

	@Then("^I get a list of matching results for Scenario Three$")
	public void i_get_a_list_of_matching_results_for_Scenario_Three() throws Throwable {
	    String text = driver .findElement(By.xpath("//h1[@class='rsHdr']")).getText();
	    Assert.assertTrue(text.contains("toys"));
	}
	@Then("^the results show more than one page$")
	public void the_results_show_more_than_one_page() throws Throwable {
		int pageCount = driver.findElements(By.xpath("//td[@class='pages']/a")).size();
		Assert.assertTrue(pageCount>1);
	}

	@Then("^the user can navigate through the pages to continue looking at the items$")
	public void the_user_can_navigate_through_the_pages_to_continue_looking_at_the_items() throws Throwable {
		int pageCount = driver.findElements(By.xpath("//td[@class='pages']/a")).size();
		
		// it will click all the pages as per the count value
		for (int i=1; i<pageCount; i++) 
		{
			driver.findElement(By.xpath("//td[@class='pages']/a[" + i + "]")).click();
			Thread.sleep(2000);
		}
	}
}
