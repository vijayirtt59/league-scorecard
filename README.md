# league-scorecard
This App lets you to calculate the ranking table for the league based on the result of individual league matches. In this league, a draw (tie) is worth 1 point and a win is worth 3 points. A loss is worth 0 points.
If two or more teams have the same number of points, they should have the same rank and be printed in alphabetical order (as in the tie for 3rd place in the sample data).

## Prerequisties

Java >= 8.0 <br />
Maven >= 3.6


## Intallation Steps

1. Download the package and unzip it

2. Open the league-scorecard folder - please confirm if pom.xml is available in this folder

3. Run ```mvn clean install``` from terminal

4. The above command will generate target folder and has scorecard-1.0.jar file

5. Place scorecard-1.0.jar file in any folder where the application has to be run(Ex. Documents)


## Running the application

1. Navigate to the folder where scorecard-1.0.jar is placed

2. Place the input file in same folder. If input file is in another folder, absolute path has be passed as parameter while running the app

3. Run the app using ```java -jar scorecard-1.0.jar input.txt```

4. Input file has be in the specified format. Please refer - https://github.com/vijayirtt59/league-scorecard/blob/4307d6438c3fc89dbbd838f854a47194750874da/input.txt

## Sample Input & Output
Input
```
Lions 3, Snakes 3
Tarantulas 1, FC Awesome 0
Lions 1, FC Awesome 1
Tarantulas 3, Snakes 1
Lions 4, Grouches 0
```
Output
```
1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
3. Snakes, 1 pt
5. Grouches, 0 pts

```
