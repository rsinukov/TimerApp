# Task  
Implement a simple countdown timer app that starts with an initial value of 2 minutes and counts down to zero. Do not spend more than half a day on this task. 

## Detailed specifications:  
* A label that shows the remaining time in Minutes, seconds and one digit for hundred milliseconds.  
* If number of minutes is zero, it displays only seconds and one digit for hundred milliseconds.  
* A "+" button. When clicked, this button increases the time remaining by 10 seconds, up to a maximum of 2 minutes.  
* If the countdown has expired (i.e., after the countdown hits 0 ms), then display the message "DONE".  
* The countdown should continue if the user opens another app.  
* If the app is killed and started again, timer starts with initial value.   

# Implemantation details  
Solution is based on Clean Architecture concept and MVI pattern for UI architecture  

## Main entities:  
`View` - entry point. Has one interesting method render that accepts view model and binds it to controls. Handles lifecycle and subscriptions  
`Presenter` - maps business entities to view entities. Also has state reducer. Handles state restoration by saving it in reduce function  
`Interactor` - connects view to business logic. Contains inside required repositories and use cases. Knows how to connect them to fulfill screen requirements  

## Important Helpers:  
`ComponentHolder` - dagger components holder, that use runtime generated keys for scope management  
`MviDelegate` - activity delegate that generates keys for dagger components  

## The reasoning behind your technical choices, including architectural.  
In my work experience I found that MVI is most extensible UI layer pattern. 
It gives easy state restoration, independent and reusable building blocks, testability, unidirectional data flow and easy debugging. 
For this task it can be an overkill, but I really wanted to show you the beauty of this pattern.  
Using `Kotlin`, `Dagger` and `RxJava` is must-have for nowadays Android development.  

## ToDo
CI integration
Checkstyle, lint, FindBugs, etc

## Bonus points 
* Kotlin gradle script inside `buildSrc` module for code completion in gradle files
* gradle files are organised and reused, see `common-gradle` folder
