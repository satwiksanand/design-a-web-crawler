package com.github.satwiksanand;

//this class has to do two things prioritization and politeness management, for now though
//i am just going to focus on politeness management for
//now the problem is to be specific, how do we implement it.
//so here is my initial idea, i will change it based on requirements though
//for mapping table i think it would be great to maintain a redis server

//I will maintain various queues in memory to store url's from a certain host
//there are two things regarding this that i think is important:
//the program should remove queues or allot that queue to some other host if the queue is empty
//second and the most important problem, how does Queue selector know which queue is ready,
//i mean just think about there could be 10000 of queue based on host and i don't want to loop over them
//everytime, so what should i do

//this is actually i think a dsa problem actually, what if  we store all the queue in a priority queue
//what if i have a priority queue which stores a pair of integers -> {next time when queue ind would be available, ind}
//then at every instant we will have which queue is ready for processing next, but we have to be careful, i don't want to add
//empty queues in the priority queue

/*TODO
* use redis instead of Map
* maybe the architecture can be a lil simple.
* */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//suppose we read a html page and extracted all the url links, and i am to store them here
public class URLFrontierImpl {
    private static final List<Queue<String>> allQueues = new ArrayList<>();
    private static final Map<String, Integer> hostMapper = new HashMap<>();//will be replaced with redis later.
    private static final PriorityQueue<Pair> nextAvailableURL = new PriorityQueue<>();

    public static String extractHost(String url) {
        // Regex to extract host from URL
        Pattern pattern = Pattern.compile("^(?:https?://)?(?:www\\.)?([^:/\\s]+)");
        Matcher matcher = pattern.matcher(url);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    public static void insertUrls(String[] allUrls){
        for(String curr : allUrls) {
            String host = extractHost(curr);
            if (host == null) {
                continue;
            }
            if (!hostMapper.containsKey(host)) {
                hostMapper.put(host, allQueues.size());
                allQueues.add(new ArrayDeque<>());
                allQueues.getLast().offer(curr);
                nextAvailableURL.add(new Pair(allQueues.size() - 1, System.currentTimeMillis()));
            } else{
                if(allQueues.get(hostMapper.get(host)).isEmpty()){
                    nextAvailableURL.add(new Pair(hostMapper.get(host), System.currentTimeMillis()));
                }
                allQueues.get(hostMapper.get(host)).offer(curr);
            }
        }
        for(Map.Entry<String, Integer> entry : hostMapper.entrySet()){
            System.out.println("host:-> " + entry.getKey());
            for(String curr : allQueues.get(entry.getValue())){
                System.out.println(curr);
            }
        }
    }

    public static String fetchNextURL(){
        if(!nextAvailableURL.isEmpty() && nextAvailableURL.peek().getValue() <= System.currentTimeMillis()){
            assert nextAvailableURL.peek() != null;
            int which = nextAvailableURL.peek().getKey();
            String res = allQueues.get(which).poll();
            if(allQueues.get(which).isEmpty()){
                nextAvailableURL.poll();
            }
            else{
                assert nextAvailableURL.peek() != null;
                nextAvailableURL.peek().setValue(nextAvailableURL.peek().getValue() + 100);
            }
            return res;
        }
        return null;
    }
}
