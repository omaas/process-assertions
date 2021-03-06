#!/usr/bin/env bash
echo "${prelude} Running Flowable integration tests";

cd integration/flowable-integration;

declare -a flowableVersions=(
                "6.0.0"
                "6.0.1"
                "6.1.0"
                "6.1.1"
                "6.1.2"
                )

echo "${prelude} Supported Flowable versions:";
for flowableVersion in "${flowableVersions[@]}"
do
   	:
	echo "${prelude}   Flowable version ${flowableVersion}";
done

for flowableVersion in "${flowableVersions[@]}"
do
   	:
	echo "${prelude} Running test profile for Flowable version ${flowableVersion}";
	mvn clean dependency:tree test -P flowable-${flowableVersion};
done

cd ../../;