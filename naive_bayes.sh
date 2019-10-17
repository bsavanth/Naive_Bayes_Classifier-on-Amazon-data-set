#!/usr/bin/env bash

echo enter first word:
read word1
echo enter second word:
read word2
echo enter window size, choices are 5,10,20
read window_size

echo enter training type, choices are:
echo 1 for unbalanced_____80% training, 20% testing__________
echo 2 for balanced_______Equal number of training/testing contexts for both words______
read type

rm -rf output
STARTTIME=$(date +%s)
rm -rf *.class
rm -rf output
javac -Xlint:none Main.java
java Main $word1 $word2 $window_size $type



ENDTIME=$(date +%s)
echo "It takes $[$ENDTIME - $STARTTIME] seconds to complete this task..."


