all:
	kotlinc-jvm src/main.kt -include-runtime -d httpd.jar -classpath lib/commons-cli-1.4/commons-cli-1.4.jar src/**/*
