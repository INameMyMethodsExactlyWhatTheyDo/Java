# Word Ladder

In this project I tried to find a path between two words in a dictionary as a word Ladder
Two words are connected if they only have one letter difference

First a undirected graph was biuld for all words
Then there is two implementations of get Word Ladder
One is BFS  (much better)
Other is DFS

DFS is capped at 300 levels so it doesn't stack overflow
DFS is also optimized by searching the path that has the most letters
in common with the target word

Word pairs to be searched for are set in the text file input.txt (/quit terminates the program)
Words must be present in the dictionary which is a text file called five_letter_words.txt
Each pair is put though a BFS and a DFS 
The resultant word ladders are shown in the file log.txt

the program take a bit of time to run

./run.sh  To run the program 
