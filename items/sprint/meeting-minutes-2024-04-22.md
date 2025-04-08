# [G35]
## Team Meeting [2] - Week [8] - [2024-04-22] (10:00 pm-12:00 midnight)
**Absent:** None
<br>
**Lead/scribe:** Anbo Wu(u7706346)

## Agreed Procedure
Stand up Procedure: 

- Make some adjustments of app function plan.
- Clarify the direction of code work for this week.
- Acceptance and display of basic functions implemented last week.



## Agenda Items
| Number |                                                  Item |
| :----- |------------------------------------------------------:|
| [1]    |              [Display the basic function performance] |
| [2]    | [Discuss which custom features should be implemented] |
| [3]    |              [Adjust function plan made in last week] |
| [4]    |                  [Assign tasks for all group members] |

## Meeting Minutes
- Last week we implemented [LogIn] and [DataFiles] features before checkpoint1 and we materialized other basic features in the weekend. Chuang Ma showed the basic function performance to us.

- We discussed all the function schedule done before and made some adjustments. Firstly, we decided that we would implement [Data-Formats] [Data-GPS] [Search-Invalid] [Search-Filter] as our custom features. We decided to realize these 'easy mode' features in the first stage fo our development. Then we will implement some other custom features based on our purpose and actual situation. 

- Here are Implementation paths of two custom features put forward by me: [Search-Invalid] and [Search-Filter]. Search invalid: Algorithm logic: Convert the string input by the user into char, search for the book name with the most char matching characters in the matching book list, and provide a list in descending order of accuracy. Problem solving: It can to some extent solve the problem of users inputting book names upside down, accidentally typing one or two letters incorrectly, and so on. Search filter: Algorithm logic: Filter by author/title/year of publication, etc. First, ensure that the book data has the above information, and then call the relevant instance for selective rendering. Problem solving: Some users may be interested in all the books of a certain author, or they may want to choose a certain year version of the book (for some annual magazines, etc.). This type of filtering can match user needs more quickly.

- The tasks assigned for next week are as follows:

  


## Action Items
| Task                         |         Assigned To         |   Due Date   |
|:-----------------------------|:---------------------------:|:------------:|
| [Basic Feature Optimization] |    [Diao Fu]&[Chuang Ma]    | [2024-04-26] |
| [GPS Feature]                | [Huizhe Ruan]&[JunFeng Gao] | [2024-04-27] |
| [Search-Invalid Feature]     |    [Chuang Ma]&[Anbo Wu]    | [2024-04-28] |
| [Search-Filter Feature]      |   [Huizhe Ruan]&[Anbo Wu]   | [2024-04-28] |
| [Overall functional evaluation]  |   [JUnfeng Gao]&[Diao Fu]   | [2024-04-29] |



## Scribe Rotation
The following dictates who will scribe in this and the next meeting.
| Name |
| :---: |
| [Anbo Wu] |
| [Diao Fu] |
| [Huizhe Ruan] |
| [Chuang Ma] |
| [Junfeng Gao] |