# Tween engine jars not currently maintained in a maven repository.
tween-engine-api-sources.jar
tween-engine-api.jar

# Running the following commands will install them to your local repository.

mvn install:install-file -Dfile=tween-engine-api.jar -DgroupId=aurelienribon.tweenengine 
-DartifactId=tween-engine-api -Dversion=6.3.3 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=tween-engine-api-sources.jar 
-DgroupId=aurelienribon.tweenengine -DartifactId=tween-engine-api-sources 
-Dversion=6.3.3 -Dpackaging=jar -DgeneratePom=true

