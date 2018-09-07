package ecore.services.errors;

import ecore.services.Service;

import java.util.Optional;

/**
 * A service that provides basic functionality for ErrorStackEntry's. You can provide an ErrorStackEntry to the
 * SerivceMessager to easily have it displayed to the players involved.
 */
public class ServiceErrorStack extends Service<ErrorStackEntry> {

    public Optional<ErrorStackEntry> getMostRecentError(){
        if(objects.size() > 0){
            ErrorStackEntry e = objects.get(objects.size() - 1);
            objects.remove(e);
            return Optional.of(e);
        }
        return Optional.empty();
    }

    //clean up the entries we no longer need
    public void tick(){
        for(ErrorStackEntry e: objects){
            if((e.getCreatedAt()/1000) >= 60){
                objects.remove(e);
            }
        }
    }

}
