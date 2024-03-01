cls
cd\
cd "C:\Users\Musabay Technologies\workspace\Training2017_OTC\GreateCourseProject\src\test\resources\seleniumServer"
REM This is a comment: starts selenium node
REM start java -jar selenium-server-standalone-3.6.0.jar -port 4858 -role node -hub http://192.168.1.161:4857/grid/register/ 
start java -jar selenium-server-standalone-3.6.0.jar -role node -hub http://192.168.1.161:4857/grid/register/ 

