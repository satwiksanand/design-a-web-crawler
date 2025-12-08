package com.github.satwiksanand;

public class ContentSeen {
    public static boolean contentSeen(String pageContent){
        //check if the hash is present already in the storage.
        String hashedPageContent = Hasher.hash(pageContent);
        if(hashedPageContent != null){
            //check hashedPageContent is present in the database or not.
            return true;
        }
        return false;
    }
}
