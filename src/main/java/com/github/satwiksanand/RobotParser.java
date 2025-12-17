package com.github.satwiksanand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A robust parser and validator for Robots.txt rules.
 * Handles User-Agent selection, Glob-to-Regex conversion, and Longest-Match precedence.
 */
public class RobotParser {

    private final List<Rule> rules;

    // Private constructor. Use the factory method 'parse' to create an instance.
    private RobotParser(List<Rule> rules) {
        this.rules = rules;
    }

    /**
     * The Main Entry Point.
     * Parses the raw lines of a robots.txt file and returns a RobotRules object
     * tailored for the specific userAgent.
     */
    public static RobotParser parse(List<String> lines, String myUserAgent) {
        List<String> targetBlockLines = findTargetBlock(lines, myUserAgent);
        List<Rule> parsedRules = new ArrayList<>();

        for (String line : targetBlockLines) {
            String cleanLine = stripComment(line);

            if (cleanLine.toLowerCase().startsWith("disallow:")) {
                String pattern = cleanLine.substring("disallow:".length()).trim();
                if (!pattern.isEmpty()) {
                    parsedRules.add(new Rule(pattern, false)); // false = Disallow
                }
            } else if (cleanLine.toLowerCase().startsWith("allow:")) {
                String pattern = cleanLine.substring("allow:".length()).trim();
                if (!pattern.isEmpty()) {
                    parsedRules.add(new Rule(pattern, true)); // true = Allow
                }
            }
        }

        return new RobotParser(parsedRules);
    }

    /**
     * Checks if a given path is allowed for crawling.
     * Follows the "Longest Match Wins" standard.
     */
    public boolean isAllowed(String path) {
        if (path == null || path.isEmpty()) return true;

        Rule bestMatch = null;

        for (Rule rule : rules) {
            if (rule.matches(path)) {
                if (bestMatch == null || rule.patternLength > bestMatch.patternLength) {
                    bestMatch = rule;
                } else if (rule.patternLength == bestMatch.patternLength) {
                    // Tie-breaker: If lengths are equal, ALLOW takes precedence over DISALLOW
                    if (rule.isAllow && !bestMatch.isAllow) {
                        bestMatch = rule;
                    }
                }
            }
        }

        // If no rule matches, access is implicitly Allowed.
        // If a rule matches, return its permission (Allow = true, Disallow = false).
        return bestMatch == null || bestMatch.isAllow;
    }

    // --- Helper Logic Below ---

    private static List<String> findTargetBlock(List<String> lines, String myUserAgent) {
        int specificIndex = -1;
        int wildcardIndex = -1;

        for (int i = 0; i < lines.size(); i++) {
            String line = stripComment(lines.get(i)).toLowerCase();
            if (line.startsWith("user-agent:")) {
                String agent = line.substring("user-agent:".length()).trim();
                if (agent.equals(myUserAgent.toLowerCase())) {
                    specificIndex = i;
                    break; // Specific match found, stop looking
                } else if (agent.equals("*")) {
                    wildcardIndex = i;
                }
            }
        }

        int startIndex = (specificIndex != -1) ? specificIndex : wildcardIndex;
        if (startIndex == -1) return Collections.emptyList(); // No rules for us

        // Collect lines until the next User-agent block starts
        List<String> block = new ArrayList<>();
        for (int i = startIndex + 1; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.toLowerCase().startsWith("user-agent:")) break;
            block.add(line);
        }
        return block;
    }

    private static String stripComment(String line) {
        int commentIndex = line.indexOf('#');
        return (commentIndex != -1) ? line.substring(0, commentIndex).trim() : line.trim();
    }

    /**
     * Inner class representing a single Line in robots.txt
     */
    private static class Rule {
        final Pattern regex;
        final int patternLength;
        final boolean isAllow;

        Rule(String globPattern, boolean isAllow) {
            this.patternLength = globPattern.length();
            this.isAllow = isAllow;
            this.regex = Pattern.compile(convertGlobToRegex(globPattern));
        }

        boolean matches(String path) {
            return regex.matcher(path).find();
        }

        private String convertGlobToRegex(String glob) {
            StringBuilder sb = new StringBuilder();
            sb.append("^"); // Anchor start
            for (int i = 0; i < glob.length(); i++) {
                char c = glob.charAt(i);
                if (c == '*') sb.append(".*");
                else if (c == '$') sb.append("$");
                else if (".?+(){}|[]\\".indexOf(c) != -1) sb.append("\\").append(c);
                else sb.append(c);
            }
            return sb.toString();
        }
    }
}