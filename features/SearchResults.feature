Feature:
As an ebay user
I should be able to search Results, where one can sort and filter 
So that I can see details of resulting products

Background:
Given I am a non-registered customer 
And I navigate to www.ebay.co.uk

@Scenario001
Scenario Outline: Search and verify results
When I search for an item "<searchWord>"
Then I get a list of "<searchWord>" matching results 
And the resulting items cards show: postage price, No of bids, price or show BuyItNow tag
Then I can sort the results by Lowest Price
And the results are listed in the page in the correct order
Then I can sort the results by Highest Price
And the results are listed in the page in the correct order
Then I can filter the results by 'Buy it now'
And all the results shown in the page have the 'Buy it now' tag 
Examples:
|searchWord  |
|perfumes    |

@Scenario002
Scenario: Search per category
When I enter a search term and select a specific Category
Then I get a list of matching results for Scenario Two
And I can verify that the results shown as per the the selected category

@Scenario003
Scenario: Search and navigate through results pages
When I search for an item
Then I get a list of matching results for Scenario Three
And the results show more than one page
Then the user can navigate through the pages to continue looking at the items
