# weather-repoort-service

This is gradle build project, It has three Service XML's to Test
In order to execute these services, you need to pass the argument 'Service' and value of desired service as given below:
1. ApiService
2. UIWeatherService
3. CompareService

1. ApiService
This will fetch the  Temprature Information from Openweather API for the given City Name and save the results to 'src/test/resources', It also ensure that API is up and running successfully.

2. UIWeatherService
This will fetch the Temprature Information from given URL mentioned in Config file and save the results to 'src/test/resources', It also ensure that UI elements are displayed and Temprature Information properly displayed. 

Important: To run this service, you need to pass and aditional argument 'BrowserName', it accept the value as string
You need to specify on which browser you would like to test
example: -DBrowserName=chrome Or -DBrowserName=firefox 

3. CompareService
This service is desgined to compare the Tempreture Results from both UI & API. This required additional argument TempMagnitudeDifference (value as int), which states that if difference of UI & API temp is more than the TempMagnitudeDifference then that test case marked as failed.
example: -DTempDiff=2
 
So inorder to run the CompareService, total 3 arguments are required:
-DService=CompareService -DBrowserName=chrome -DTempDiff=2
