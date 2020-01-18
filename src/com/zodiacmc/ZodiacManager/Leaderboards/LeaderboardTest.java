package com.zodiacmc.ZodiacManager.Leaderboards;

class LeaderboardTest {

    private static boolean failed;


    private LeaderboardTest() { }


    public static void main(String[] args) {
        // Test the ArrayLeaderboard class.
        test(new ArrayLeaderboard<String, Integer>());

        // Test the LinkedLeaderbaord class.
        // test(new LinkedLeaderboard<String, Integer>());
    }


    /**
     * Fuck me.
     * @param lb fuck
     * @return me
     */
    static boolean test(ILeaderboard<String, Integer> lb) {
        lb.add("Foo", 64);
        lb.add("Bar", 48);
        lb.add("FooBar", 96);
        lb.add("Banana", 16);
        lb.add("CoolGuy", 0);
        lb.add("PajamaMan", 32);
        lb.add("Toaster", -16);
        lb.add("1337", 80);

        // The first toString should be equal to this
        String ts1 = "1. FooBar: 96\n"
                    + "2. 1337: 80\n"
                    + "3. Foo: 64\n"
                    + "4. Bar: 48\n"
                    + "5. PajamaMan: 32\n"
                    + "6. Banana: 16\n"
                    + "7. CoolGuy: 0\n"
                    + "8. Toaster: -16";


        if (!lb.toString().equals(ts1)) {
            System.out.println("Expected:\n" + ts1);
            System.out.println("\nActual:\n" + lb);
            failure("*** Error in add(K, V) or toString() method.");
        } else {
            System.out.println("add(K, V) is correct!");
            System.out.println("toString() is correct!");
        }

        if (lb.get("Foo") != 64 || lb.get("Toaster") != -16 || lb.get("PajamaMan") != 32 || lb.get("PENIS") != null) {
            failure("*** Error in get(K) method.");
        } else {
            System.out.println("get(K) method is correct!");
        }

        if (!lb.containsKey("1337") || !lb.containsKey("Banana") || !lb.containsKey("Bar") || lb.containsKey("Penis")) {
            failure("*** Error in containsKey(K) method.");
        } else {
            System.out.println("containsKey(K) method is correct!");
        }

        if (!lb.containsValue(96) || !lb.containsValue(64) || !lb.containsValue(-16) || lb.containsValue(10)) {
            failure("*** Error in containsValue(V) method.");
        } else {
            System.out.println("containsValue(V) method is correct!");
        }

        if (lb.size() != 8) {
            failure("*** Error in size() method.");
        }

        ILeaderboard.Entry<String, Integer> rm1 = lb.remove("Toaster");
        ILeaderboard.Entry<String, Integer> rm2 = lb.remove("CoolGuy");
        ILeaderboard.Entry<String, Integer> rm3 = lb.remove("1337");
        ILeaderboard.Entry<String, Integer> rm4 = lb.remove("Its 2:19 AM rn");

        // Its so fucking late. ;p
        if ((!rm1.getKey().equals("Toaster") && rm1.getValue() != -16) ||
            (!rm2.getKey().equals("CoolGuy") && rm2.getValue() != 0) ||
            (!rm3.getKey().equals("1337") && rm3.getValue() != 80) || 
            (rm4 != null)) {
            
            failure("*** Error in remove(K) method.");
        } else {
            if (lb.size() == 5) {
                System.out.println("remove(K) method is correct!");
                System.out.println("size() method is correct!");
            } else {
                failure("*** remove(K) or size() method is incorrect.");
            }
        }

        if (lb.getPosition("PajamaMan") != 4) {
            failure("*** Error in getPosition(K) method.");
            System.out.println("Got:" + lb.getPosition("PajamaMan"));
            System.out.println("Expected: 4");
        } else {
            System.out.println("getPosition(K) method is correct!");
        }

        ILeaderboard.Entry<String, Integer> ge1 = lb.getEntry(4);
        ILeaderboard.Entry<String, Integer> ge2 = lb.getEntry(1);
        ILeaderboard.Entry<String, Integer> ge3 = lb.getEntry(67);
        if ((!ge1.getKey().equals("FooBar") && ge1.getValue() != 96) ||
            (!ge2.getKey().equals("PajamaMan") && ge2.getValue() != 32) ||
            (ge3 != null)) {
            failure("*** Error in getEntry(int) method.");
        } else {
            System.out.println("getEntry(int) method is correct!");
        }

        lb.clear();
        if (lb.size() != 0) {
            failure("*** Error in clear() method.");
        }


        return failed;
    }


    private static void failure(String message) {
        System.err.println(message);
        failed = true;


        // Fuck unit tests.
    }
}