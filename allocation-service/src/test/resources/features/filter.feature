Feature: Tests the allocation Service using a REST client.
 
Scenario Outline: Filter the allocation by branch and product and entity using URL <filter_url>
Given that "tenant" equals "tenant0"
And that "employee" equals "E1"
When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
And I construct a REST request with header "x-chenile-eid" and value "${employee}"
And I POST a REST request to URL "/allocation/<filter_url>" with payload
"""json
{
  "allocatableEntity": {
    "branch": "NYC",
     "product": "Savings",
     "entity": "Retail"
  }
}
"""
Then success is true
#And store "$.payload.size" from response to "id"
And the REST response key "size()" is "6"
  Examples:
    |filter_url  |
    |filter  |


Scenario Outline: Branch and Product with LAX and Checking  using URL <filter_url>
Given that "tenant" equals "tenant0"
And that "employee" equals "E1"
When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
And I construct a REST request with header "x-chenile-eid" and value "${employee}"
And I POST a REST request to URL "/allocation/<filter_url>" with payload
"""json
{
  "allocatableEntity": {
          "branch": "LAX",
          "product": "Checking"
   }
}
"""
Then success is true
#And store "$.payload.size" from response to "id"
And the REST response key "size()" is "6"
  Examples:
    |filter_url  |
    |filter  |


Scenario Outline: Filter users by entity Retail using URL <filter_url>
Given that "tenant" equals "tenant0"
And that "employee" equals "E1"
When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
And I construct a REST request with header "x-chenile-eid" and value "${employee}"
And I POST a REST request to URL "/allocation/<filter_url>" with payload
"""json
{
  "allocatableEntity": {
    "entity": "Retail"
  }
}
"""
Then success is true
And the REST response key "size()" is "6"
  Examples:
    |filter_url  |
    |filter  |


  Scenario Outline: Filter users by branch and product using URL <filter_url>
Given that "tenant" equals "tenant0"
And that "employee" equals "E1"
When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
And I construct a REST request with header "x-chenile-eid" and value "${employee}"
And I POST a REST request to URL "/allocation/<filter_url>" with payload
"""json
{
  "allocatableEntity": {
      "branch": "NYC",
      "product": "Checking"
  }
}
"""
Then success is true
And the REST response key "size()" is "6"
    Examples:
      |filter_url  |
      |filter      |


  Scenario Outline: Filter users by branch and entity using URL <filter_url>
Given that "tenant" equals "tenant0"
And that "employee" equals "E1"
When I construct a REST request with header "x-chenile-tenant-id" and value "${tenant}"
And I construct a REST request with header "x-chenile-eid" and value "${employee}"
And I POST a REST request to URL "/allocation/<filter_url>" with payload
"""json
{
  "allocatableEntity": {
      "branch": "LAX",
      "entity": "Commercial"
   }
}
"""
Then success is true
And the REST response key "size()" is "6"
    Examples:
      |filter_url  |
      |filter      |





    