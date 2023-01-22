# Grid word solver

This application takes a string input and tries to find a word square of the inputted characters of the inputted length

A word square looks like :

R o s e

O v e n

S e n d

E n d s

where each word can be spelt horizontally and vertically.

##How to use

Run Main.java as a java application

The program will give a list of instructions on how to use

It is as easy as inputting a string in the textbox in the format of N N^2 character where N is the character length of the word such as '4 eeeeddoonnnsssrv'

If wanting more specific logging, edit the verbose variable in WordSquare and Dictionary class to true

##How it works

The program uses a wide range of technology to solve this problem. 

Like previously stated, the words.txt file is loaded into a hashing table which provides a fast and efficient solution to validating words

Once the table is filled, the user has to input a string in the format the program provides. The program will sanitise the input and make sure its valid

When the input is taken, it is sent to be transformed into a frequency map of each character. 

With the frequency map, a list of permutations of the original string is found from the map with every combination of letter. 

When the list of possible words is generated it is passed into the hashtable to confirm if the potential word can be matched to a valid word in the hash table

With the list of confirmed words, it is then passed into the WordSquare class where it will be turned into a TriNode tree.

Each word is defined as a new node in the tree, the word is set as the root of the node. Each iteration of the for loop will find the character at the current index and calculates the index of the array by subtracting the ascii value of a. If the next node of the index is null then we create a new node in the tree and assigns it to the next open array at that index. The node is finally updated to the next node at the pre calculated index

Once the tree structure is made then we have to traverse it to find any and every possible combination that results in a word square

The program will then recursively search the tree for each node in the tree and backtrack through to check each combination of word to determine whether the word square pattern can be matched.

Once a potential word square is solved, each "solution" will be converted into a character frequency map and then compared against the original input character frequency map to determine whether the solution is actually valid or not based on the initial characters its given.

The program includes a test suite as well with various inputs of different length

##Dev Log

###Initial plan

Split problem into a few main parts

Need an "anagram" solver for the string of characters

Hook into a dictionary API to validate potential words

Need some form of method to combine potential words into potential sets 

Need a word square validator

=========================Edit=============================

dictionary API is off the cards due to an insane amount of data and would be infeasible to use an API as a solution, new plan is to have a local dictionary txt file

#####Anagram Solver
Using the string of characters in the problem, find each permutation in the string of n length where n is the initial number in the input string (word size)

pass each potential n length word into the dictionary service and validate whether its a word or not

this should result in a big list of words that could be made within the string

create a frequency map of characters in the initial string for use later

These functions should exist in a utility class or its own independent anagram class

#####Dictionary
the dictionary should have its own class

import a large words.txt file that contains all of the english language

this could be inefficient down the line and could parse the txt file later and split it into 4,5,6,etc length character dictionaries to speed up initial parsing

Load the txt into memory and compare potential words against the dictionary in a dictionary class

####Combination generator
Best way to solve I can think of is to parse each confirmed word in the initial string

for each word start parsing the words again trying to find a the next word that starts with the second character of the initial word

for example the word moan

the next word would need to check its starting character is 'o'

the word after would check if the starting character is 'a' and so on. 

this would need to recursively call itself for every word and every order else there's chance it will miss combinations due to acceptig the first instance that validates the checks.

+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Another potential approach would be to use the character frequency map from early, generate more permutations of each combination of 4 string sets

generate a character frequency map for each set of strings and compare it against the original, if the two are equal then try validate a word square

####Word square validation
Finally, once all other parts are done we can easily check if the set contains a word square 


####Updates

The initial 2 ways I thought about to generate potential word squares didn't work. Each process had its own problems. 

Firstly, trying to match words together by finding words that started with the 2nd character of the first word etc had a few bugs. This might have been due to how I was trying to handle the recursion calls as I couldnt find a good way of making a stopping condition. The alogorithm itself would also miss some combinations, this process would also take a long time to find matches. This way isnt feasible

The second way I tried with trying to create word sets that match the frequency map also could have worked. However, this would take a very long time as well, and I dont think it was as efficient as it could be. My algorithm was more of a brute force solution and would fail to scale to the longer input strings so this had to be scrapped also. 

Back to the drawing board, I did some extra research on the problem to see if anyone online was doing similar things. There is a popular leetcode on the subject however this problem isnt as straightforward as the leetcode example so any inspiration found from other solutions had to be modified. 

I noticed a lot of solutions used some form of tree structure so I opted to try it. I made up a TriNode structure that holds 26 instances of nested TriNodes for each letter of the alphabet and chose to insert nodes into the tree holding the word value string and each letter contained in the word. 

the hard part I encountered was traversing the tree, I knew backtracking through the tree to find matches was the right call but trying to implement the recursion algorithm proved tricky. I found an example of someone implementing a similar style of recursion to solve a similar tree traversal problem so I plugged bits of his recursion into my search function and modified it to fit my intended solution to find word square matches as well. 

After I had a solution I was comfortable with that generated actual word squares from the list of potential words from the input string I had to move onto the next step

The frequency map of characters I made earlier in the process came in very useful here as for each of the potential word squares, i concatenated the strings into character lists, turned the lists into another frequency map. So there'd be a frequency map of each new potential word square and then compared it against the original map to ensure the new word squares only contained the correct amount and quantities of characters.

This solution works perfectly albeit a bit slow, but I've narrowed it down to when the program generates permutations of different N length words and then compares them against the dictionary. This is very slow and doesnt scale well to the 5 length words. 

Because the amount of potential permutations from the input string exponentially increases when more and more characters are added the time complexity will increase exponentially ( log(n) ) as well so another solution has to be found

Potential solutions to this are to improve the parsing of the txt file and/or parse the dictionary txt file and then copy words into separate the dictionary into smaller dictionaries for 4/5/6/7/etc length words 

further research into this shows a hashtable to hold the dictionary file in memory allows for fast lookups 

Implementation of a hashtable has greatly improved efficiency. 

4 word problems have had their time reduced from 15 seconds to 50-100ms, 5 word problems have been reduced from 25+ minutes to 200-500ms and 7 word problems have been reduced from probably months to 2 minutes. 

###Further Improvements

-Switch from System.Out as debugging tool and move log lining to a logging framework such as SLF4J


-Minor improvement would be to optimise the dictionary file itself and split into 3/4/5/6/etc character length words (minor minor improvement)


-optimise the permutation generator, it's okay for up to 6 length words but 7+ and it takes a minute or two to generate every permutation

###TODO

-Currently the program has capability to return more than one solved word square for an input string. For example, during development I found this string '7 aaaaaaaaabbeeeeeeedddddggmmlloooonnssssrrrruvvyyy' from the example has actually 3 solutions but due to the nature of how the word square is being handled and returned it caused bugs elsewhere in the code. If i had more time, I would implement a fix to this and alert the user if more than one word square can be found