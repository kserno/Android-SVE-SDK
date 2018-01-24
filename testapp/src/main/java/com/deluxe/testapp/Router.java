package com.deluxe.testapp;

import java.util.List;

/**
 * Created by filipsollar on 22.1.18.
 */

public interface Router {

    /**
     * adds next fragments to stack [A B] -> [A B C]
     * @param fragment to be added at end of stack
     */
    void goTo(BaseFragment fragment);

    /**
     * replaces last fragment with other new fragment [A B] -> [A C]
     * @param fragment to replace last item of stack
     */
    void replace(BaseFragment fragment);

    /**
     * replaces the whole stack with another [A B] -> [C B D] for example
     * @param fragments new stack
     */
    void replaceAllWith(List<BaseFragment> fragments);

    /**
     * resets the stack to only one fragment [A B C] -> [D]
     * @param fragment to replace the whole stack
     */
    void reset(BaseFragment fragment);

    /**
     * deletes last fragment from the stack [A B C] -> [A B]
     */
    void goBack();

}
