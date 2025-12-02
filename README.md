# Designing a web crawler

## Introduction:

### What is a Web Crawler?
A web crawler is a computer application which is like a robot, it is widely used to discover new
or updated content on the web, these content can include text, image, video, pdf or anything.

A web crawler starts by collecting some web pages first and then follows the links
on those pages to collect new content

### What are the applications of a web crawler?
1. this is the  most common use case is search engine indexing, a crawler collects web pages
    to create local index for search engines.
2. web mining can be done to extract useful knowledge from the web.

>Note: First, I am planning to build a simple model of a web crawler without worrying about scalability.

## A simplistic model working model in java(single threaded):

