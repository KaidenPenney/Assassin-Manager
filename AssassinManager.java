import java.util.*;

public class AssassinManager {

    private AssassinNode killring;
    private AssassinNode graveyard;
    
    /**
    In this method, an IllegalArgumentException is thrown if the names list is empty.
    When contructing the linked list with the names, I'm constructing it backwards
    in order to not have to iterate through using current example in class.
    By setting killring equal to null, this builds starting at the back
    then using a for loop takes in the size and adds to the front everytime, the back
    name creating the list without iterating through using next method. Killring is treated
    as front.
    @param a list of names that is being contructed for the game
    */
    public AssassinManager (List<String> names) {
       if(names == null || names.isEmpty()) throw new IllegalArgumentException("no people in linked list");
       killring = null;
       for(int i = names.size()-1; i >= 0; i--) {
         killring = new AssassinNode(names.get(i), killring);
       }
        
    }

    /**
    In this method, we use the current iteration. While the next node isnt null
    we say the current data/name is stalking the next nodes data/name. Once the next
    node is null, we take the currents name and say its stalking killring.name which
    points to the front, meaning that if only one name, current.name and killring.name
    will point to same node.
    */
    public void printKillRing() {
        AssassinNode current = killring;
        while(current.next != null){
           System.out.println("    "+current.name + " is stalking " + current.next.name);
           current = current.next;
        }
        System.out.println("    "+current.name + " is stalking " + killring.name);
        
    }
 
    /**
    Using the AssassinNode current, we iterate through the graveyard list and
    while the next node isn't null, we say the current node was killed by the current.killer
    which is awesome because it just gives us the killer of the current node. The kill method
    sorts alphabetically which will be printed using this method.
    */   
    public void printGraveyard() {
      AssassinNode current = graveyard;
      while (current != null) {
        System.out.println("    " + current.name + " was killed by " + current.killer);
        current = current.next;
    }
}
 
    /**
    This method iterates through the killring list using current. If the name
    at the current node is equal to the name in the parameter, it return true.
    Otherwise it continues iterating and if none is false.
    @param the name that is wanting to be found in the killring list.
    @return true if there is a node that contains the parameter name otherwise false
    */   
    public boolean killRingContains (String name) {
        AssassinNode current = killring;
        while(current != null) {
           if(current.name.equalsIgnoreCase(name)){
               return true;
           }
           current = current.next;
        }
        return false;
    }
 
    /**
    This method iterates through the graveyard list using current. If the name
    at the current node is equal to the name in the parameter, it return true.
    Otherwise it continues iterating and if none is false.
    @param the name that is wanting to be found in the gradeyard list.
    @return true if there is a node that contains the parameter name otherwise false
    */ 
    public boolean graveyardContains (String name) {
        AssassinNode current = graveyard;
        while(current != null) {
           if(current.name.equals(name)) {
               return true;
           }
           current = current.next;
        }
        return false;
    }
 
    /**
    If there is more or less than one node in the killring list it returns false,
    otherwise true.
    @return whether or not the game has ended
    */   
    public boolean gameOver() {
        if(killring != null && killring.next == null) {
            return true;
        }
        else
            return false;
    }

    /**
    In this method, the if statement checks if there is more or less than one node. If so,
    return is null
    @return the front node's data being the name of the winner
    @return null if more or less than one node
    */   
    public String winner() {
         if(gameOver()) return killring.name;
         return null;
    }
   
    /**
    There are two illegal argument exceptions: if game is already over and if the list doesn't
    contain the name in the parameter. We iteratre through the killring using current until the node
    we are at is the node that will be getting killed. If the person being killed is at the front of the list,
    we take a new node killer and set it to killring and iterate until we get to last node. We take the string
    killerName and take in the name then say killring=killring.next, killing the victim. If not in front of list,
    the killerName is set to previous node and our current=current.next eliminating the node. Now, we add the deleted node
    into the graveyard linked list. If the graveyard is empty or our node comes first alphabetically, we insert at front moving other nodes.
    Otherwise we iterate until the node fits and then insert. This way the graveyard linked list is alphabetical.
    @param the name that will be being killed and sent to graveyard LinkedList
    */
    public void kill (String name) {
         
        if(gameOver())
            throw new IllegalStateException("Game ended already");
        if(!killRingContains(name))
            throw new IllegalArgumentException("Person not in game"); 
        AssassinNode current = killring;
        AssassinNode prev = null;
        
        //LOCATE VICTIM AND FIND KILLER THEN ELIMINATE
        while (current != null && !current.name.equalsIgnoreCase(name)) {
            prev = current;
            current = current.next;
        }
        String killerName;
        //if victim is at front killer is at back
        if(current == killring){
            AssassinNode killer = killring;
            while(killer.next != null) {
               killer = killer.next; //killer is last node in list
            }
            killerName = killer.name;
            killring = killring.next; //kill him
        }
        else{
            killerName = prev.name;
            prev.next = current.next; //kill him but save current to add to graveyard
        }
        current.killer = killerName; //initialize the current persons killer using the killerName
        
        
        //ADD INTO GRAVEYARD (A-Z)
        if(graveyard == null || current.name.compareToIgnoreCase(graveyard.name) < 0) {
            current.next = graveyard;
            graveyard = current;
        }
        else {
            AssassinNode gravCurrent = graveyard;
            while(gravCurrent.next != null && current.name.compareToIgnoreCase(gravCurrent.next.name) > 0) {
                  gravCurrent = gravCurrent.next;
            }
            current.next = gravCurrent.next;
            gravCurrent.next = current;
        }
    }
}
