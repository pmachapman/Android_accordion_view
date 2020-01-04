package com.riyagayasen.easyaccordion;

/*
 * Created by riyagayasen on 24/10/16.
 */

/***
 * This interface acts as a listener for the expansion and collapse of the accordion
 */
public interface AccordionExpansionCollapseListener {
    void onExpanded(AccordionView view);

    void onCollapsed(AccordionView view);
}
