# Designing a web crawler

## Introduction:

### What is a Web Crawler?
A web crawler is a computer application which is like a robot, it is widely used to discover new
or updated content on the web, these content can include text, image, video, pdf or anything.

A web crawler starts by collecting some web pages first and then follows the links
on those pages to collect new content

![web-crawler-demonstration.png](assets/web-crawler-demonstration.png)

### What are the applications of a web crawler?
1. this is the  most common use case is search engine indexing, a crawler collects web pages
    to create local index for search engines.
2. web mining can be done to extract useful knowledge from the web.

>Note: First, I am planning to build a simple model of a web crawler without worrying about scalability.

## A simplistic model working model in java(single threaded):

the simple version goes something like this:

![web-crawler-v1.png](assets/web-crawler-v1.png)

1. fetch url: module to make fetch HTML contents of web pages.
2. parse HTML: parse the HTML content to extract data.
3. extract links: extract links in the web page and add them if they are not already
    explored.
4. store the web page content in a database, for our case we go for mongoDB
5. add to queue: add the extracted links(if not explored already) to the queue.

for this version, I am using HashMaps to store data instead of a database.
