package com.riyagayasen.easyaccordion;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by riyagayasen on 08/10/16.
 */

@SuppressWarnings("unused")
public class AccordionView extends RelativeLayout {

    View[] children;

    Boolean isExpanded = false;

    Boolean isAnimated = false;

    Boolean isPartitioned = false;

    String headingString;

    View partition;

    TextView heading;

    RelativeLayout paragraph;

    int headingTextSize;

    ImageView dropDownImage;

    ImageView dropUpImage;
    LinearLayout headingLayout;
    int paragraphTopMargin;
    int paragraphBottomMargin;
    int headingBackgroundColor = Color.WHITE;

    // int paragraphHeight;
    int paragraphBackgroundColor = Color.WHITE;
    Drawable headingBackground;
    Drawable paragraphBackground;
    AccordionExpansionCollapseListener listener;
    final OnClickListener toggleParagraphVisibility = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (paragraph.getVisibility() == VISIBLE) {
                collapse();
            } else
                expand();
        }
    };
    private LayoutInflater inflater;

    /***
     * Constructor taking only the context. This is useful in case
     * the developer wants to programmatically create an accordion view.
     *
     * @param context The context.
     */
    public AccordionView(Context context) {
        super(context);
        prepareLayoutWithoutChildren(context);
    }

    /***
     * The constructor taking an attribute set. This is called by the android OS itself,
     * in case this accordion component was included in the layout XML itself.
     * @param context The context.
     * @param attrs The attributes.
     */
    public AccordionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        handleAttributeSet(context, attrs);
    }

    /***
     * Same as the constructor AccordionView(Context context, AttributeSet attrs)
     * @param context The context.
     * @param attrs The attributes.
     * @param defStyleAttr The default style attribute.
     */
    public AccordionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleAttributeSet(context, attrs);
    }

    /***
     * A function that takes the various attributes defined for this accordion. This accordion extends
     * a relative layout. There are certain custom attributes that I have defined for this accordion whose values need
     * to be retrieved.
     * @param context The context.
     * @param attrs The attributes.
     */
    private void handleAttributeSet(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AccordionView);
        isAnimated = a.getBoolean(R.styleable.AccordionView_isAnimated, false);
        isPartitioned = a.getBoolean(R.styleable.AccordionView_isPartitioned, false);
        headingString = a.getString(R.styleable.AccordionView_heading);
        isExpanded = a.getBoolean(R.styleable.AccordionView_isExpanded, false);
        headingTextSize = a.getDimensionPixelSize(R.styleable.AccordionView_headingTextSize, 20);
        if (WidgetHelper.isNullOrBlank(headingString))
            throw new IllegalStateException("Please specify a heading for the accordion");

        headingBackgroundColor = a.getColor(R.styleable.AccordionView_headingBackgroundColor, 0);
        paragraphBackgroundColor = a.getColor(R.styleable.AccordionView_bodyBackgroundColor, 0);

        headingBackground = a.getDrawable(R.styleable.AccordionView_headingBackground);
        paragraphBackground = a.getDrawable(R.styleable.AccordionView_bodyBackground);
        a.recycle();
    }

    /***
     * This creates an accordion layout. This is called when the user programmatically creates an accordion. 'Without Children' signifies that no UI elements
     * have been added to the body of the accordion yet.
     * @param context The context.
     */
    private void initializeViewWithoutChildren(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            LinearLayout accordionLayout = (LinearLayout) inflater.inflate(R.layout.accordion, this, false);
            partition = accordionLayout.findViewById(R.id.partition);
            heading = accordionLayout.findViewById(R.id.heading);
            paragraph = accordionLayout.findViewById(R.id.paragraph_layout);
            dropDownImage = accordionLayout.findViewById(R.id.dropDown_image);
            dropUpImage = accordionLayout.findViewById(R.id.dropUp_image);
            headingLayout = accordionLayout.findViewById(R.id.heading_layout);
            paragraph.removeAllViews();
            removeAllViews();
            paragraphBottomMargin = ((LinearLayout.LayoutParams) paragraph.getLayoutParams()).bottomMargin;
            paragraphTopMargin = ((LinearLayout.LayoutParams) paragraph.getLayoutParams()).topMargin;
            addView(accordionLayout);
        }

    }

    /***
     * This function is called when the accordion is added in the XML itself and is used to initialize the various components
     * of the accordion
     * @param context The context.
     */
    private void initializeViews(Context context) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            LinearLayout accordionLayout = (LinearLayout) inflater.inflate(R.layout.accordion, this, false);
            partition = accordionLayout.findViewById(R.id.partition);
            heading = accordionLayout.findViewById(R.id.heading);
            paragraph = accordionLayout.findViewById(R.id.paragraph_layout);
            dropDownImage = accordionLayout.findViewById(R.id.dropDown_image);
            dropUpImage = accordionLayout.findViewById(R.id.dropUp_image);
            headingLayout = accordionLayout.findViewById(R.id.heading_layout);
            paragraph.removeAllViews();

            int i;
            children = new View[getChildCount()];
            for (i = 0; i < getChildCount(); i++) {
                children[i] = getChildAt(i);
            }
            removeAllViews();
            for (i = 0; i < children.length; i++) {
                paragraph.addView(children[i]);
            }

            paragraphBottomMargin = ((LinearLayout.LayoutParams) paragraph.getLayoutParams()).bottomMargin;
            paragraphTopMargin = ((LinearLayout.LayoutParams) paragraph.getLayoutParams()).topMargin;

            addView(accordionLayout);
        }
    }

    /***
     * This function; after initializing the accordion, performs necessary UI operations like setting the partition or adding animation or
     * expanding or collapsing the accordion
     * @param context The context.
     */
    private void prepareLayout(Context context) {
        initializeViews(context);
        partition.setVisibility(isPartitioned ? VISIBLE : INVISIBLE);
        heading.setText(headingString);
        heading.setTextSize(headingTextSize);

        //Set the background on the heading...if the background drawable has been set by the user, use that. Else, set the background color
        if (!WidgetHelper.isNullOrBlank(headingBackground))
            headingLayout.setBackground(headingBackground);
        else if (!WidgetHelper.isNullOrBlank(headingBackgroundColor))
            headingLayout.setBackgroundColor(headingBackgroundColor);

        //Set the background on the paragraph...if the background drawable has been set by the user, use that. Else, set the background color
        if (!WidgetHelper.isNullOrBlank(paragraphBackground))
            paragraph.setBackground(paragraphBackground);
        else if (!WidgetHelper.isNullOrBlank(paragraphBackgroundColor))
            paragraph.setBackgroundColor(paragraphBackgroundColor);

        paragraph.setVisibility(VISIBLE);
        //paragraph.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        if (isAnimated) {
            headingLayout.setLayoutTransition(new LayoutTransition());
        } else {
            headingLayout.setLayoutTransition(null);

        }

        if (isExpanded)
            expand();
        else
            collapse();

        setOnClickListenerOnHeading();

    }

    /***
     * This function is used to prepare the layout after the initialize function but is called when the developer PROGRAMMATICALLY adds
     * the accordion from the class. Hence, the accordion does not have the UI elements (children) yet
     * @param context The context.
     */
    private void prepareLayoutWithoutChildren(Context context) {
        initializeViewWithoutChildren(context);
        partition.setVisibility(isPartitioned ? VISIBLE : INVISIBLE);
        heading.setText(headingString);
        paragraph.setVisibility(VISIBLE);
        //paragraph.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);
        if (isAnimated) {
            headingLayout.setLayoutTransition(new LayoutTransition());
        } else {
            headingLayout.setLayoutTransition(null);

        }

        if (isExpanded)
            expand();
        else
            collapse();

        setOnClickListenerOnHeading();

    }

    @Override
    protected void onFinishInflate() {
        prepareLayout(getContext());
        super.onFinishInflate();

    }

    /***
     * This function expands the accordion
     */
    private void expand() {
        if (isAnimated) {

            AccordionTransitionAnimation expandAnimation = new AccordionTransitionAnimation(paragraph, 300, AccordionTransitionAnimation.EXPAND);
            expandAnimation.setHeight(WidgetHelper.getFullHeight(paragraph));
            expandAnimation.setEndBottomMargin(paragraphBottomMargin);
            expandAnimation.setEndTopMargin(paragraphTopMargin);
            paragraph.startAnimation(expandAnimation);

        } else {
            paragraph.setVisibility(VISIBLE);
        }


        partition.setVisibility(isPartitioned ? VISIBLE : INVISIBLE);

        dropUpImage.setVisibility(VISIBLE);
        dropDownImage.setVisibility(GONE);
        if (!WidgetHelper.isNullOrBlank(listener)) {
            listener.onExpanded(this);
        }


    }

    /***
     * This function collapses the accordion
     */
    private void collapse() {

        if (isAnimated) {

            AccordionTransitionAnimation collapseAnimation = new AccordionTransitionAnimation(paragraph, 300, AccordionTransitionAnimation.COLLAPSE);
            paragraph.startAnimation(collapseAnimation);

        } else {
            paragraph.setVisibility(GONE);

        }

        partition.setVisibility(INVISIBLE);

        dropUpImage.setVisibility(GONE);
        dropDownImage.setVisibility(VISIBLE);

        if (!WidgetHelper.isNullOrBlank(listener)) {
            listener.onCollapsed(this);
        }
    }

    private void setOnClickListenerOnHeading() {
        heading.setOnClickListener(toggleParagraphVisibility);
        dropDownImage.setOnClickListener(toggleParagraphVisibility);
        dropUpImage.setOnClickListener(toggleParagraphVisibility);

    }

    /***
     * This function adds the view to the body or the 'paragraph'
     * @param child The child view.
     */
    public void addViewToBody(View child) {
        paragraph.addView(child);
    }

    /***
     * Set the heading of the accordion
     * @param headingString The heading string.
     */
    public void setHeadingString(String headingString) {
        heading.setText(headingString);
    }


    public void setIsAnimated(Boolean isAnimated) {
        this.isAnimated = isAnimated;
    }

    /***
     * Get the status whether the accordion is going to animate itself on expansion or collapse
     * @return A boolean value.
     */
    public Boolean getAnimated() {
        return isAnimated;
    }

    /***
     * Set whether the accordion would play an animation when expanding/collapsing
     * @param animated A boolean value.
     */
    public void setAnimated(Boolean animated) {
        isAnimated = animated;
    }

    /***
     * Tell the accordion what to do; when expanded or collapsed.
     * @param listener The listener.
     */
    public void setOnExpandCollapseListener(AccordionExpansionCollapseListener listener) {
        this.listener = listener;
    }

    /***
     * This function returns the body of the accordion
     * @return The relative layout.
     */
    public RelativeLayout getBody() {
        return paragraph;
    }

    /***
     * This function returns the body of the accordion
     * @return The relative layout.
     */
    public RelativeLayout getParagraph() {
        return paragraph;
    }

    public Boolean getExpanded() {
        return isExpanded;
    }

    /***
     * Tell the accordion whether to expand or remain collapsed by default, when drawn
     * @param expanded A boolean value.
     */
    public void setExpanded(Boolean expanded) {
        isExpanded = expanded;
    }

    /***
     * The the status of the partition line
     * @return A boolean value.
     */
    public Boolean getPartitioned() {
        return isPartitioned;
    }

    /***
     * This function tells the accordion whether to make the partition visible or not
     * @param partitioned A boolean value.
     */
    public void setPartitioned(Boolean partitioned) {
        isPartitioned = partitioned;
        partition.setVisibility(isPartitioned ? VISIBLE : INVISIBLE);
    }

    /***
     * This function adds a background drawable to the heading. Works only for JellyBean and above
     * @param drawable THe drawable.
     */
    public void setHeadingBackground(Drawable drawable) {

        if (WidgetHelper.isNullOrBlank(headingLayout))
            headingLayout = findViewById(R.id.heading_layout);

        headingLayout.setBackground(drawable);
    }


    /***
     * This function adds a background drawable to the heading. Works only for JellyBean and above
     * @param resId The resource id.
     */
    public void setHeadingBackground(int resId) {
        Drawable drawable = getResources().getDrawable(resId);

        if (WidgetHelper.isNullOrBlank(headingLayout))
            headingLayout = findViewById(R.id.heading_layout);

        headingLayout.setBackground(drawable);
    }

    /***
     * This function adds a background drawable to the paragraph. Works only for JellyBean and above
     * @param drawable The drawable
     */
    public void setBodyBackground(Drawable drawable) {

        if (WidgetHelper.isNullOrBlank(paragraph))
            paragraph = findViewById(R.id.paragraph_layout);

        paragraph.setBackground(drawable);
    }

    /***
     * This function adds a background drawable to the paragraph. Works only for JellyBean and above
     * @param resId The resource id.
     */
    public void setBodyBackground(int resId) {
        Drawable drawable = getResources().getDrawable(resId);

        if (WidgetHelper.isNullOrBlank(paragraph))
            paragraph = findViewById(R.id.paragraph_layout);

        paragraph.setBackground(drawable);
    }

    /***
     * This function adds a background color to the heading.
     * @param color The color.
     */
    public void setHeadingBackGroundColor(int color) {

        if (WidgetHelper.isNullOrBlank(headingLayout))
            headingLayout = findViewById(R.id.heading_layout);

        headingLayout.setBackgroundColor(color);
    }

    /***
     * This function adds a background color to the paragraph.
     * @param color The color.
     */
    public void setBodyBackgroundColor(int color) {

        if (WidgetHelper.isNullOrBlank(paragraph))
            paragraph = findViewById(R.id.paragraph_layout);

        paragraph.setBackgroundColor(color);
    }
}
