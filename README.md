# AmazonElastiCacheExample [![Build Status](https://travis-ci.org/fishercoder1534/AmazonElastiCacheExample.svg?branch=master)](https://travis-ci.org/fishercoder1534/AmazonElastiCacheExample)

As per AWS documentation, your app must be in the same VPC and your security group of Elasticache clusters/instances must have proper settings, in order to access your Elasticache clusters/instances.

How to run this example:
```
1. git clone this repo
2. cd $this_directory
3. $mvn package
4. $java -jar aws-elasticache-example-1.0-SNAPSHOT-jar-with-dependencies.jar
```