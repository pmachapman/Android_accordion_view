# Android_accordion_view
A very easy to use accordion component for android. 

I created this component because I wanted a component that could behave as an accordion, where in, the developer could set the heading and the UI elements in the body right from the layout xml or from the class. I also wanted to have various other UI features like animation to be controlled by the developer from the xml itself. 

To add the Accordion component,(Note: this project was built using Android Studio 2.2)
  1. Take the 'easyaccordion' folder and save it in your project's root directory (the directory containing the 'app' folder).  
  2. Add 
  
          'compile project(path: ':easyaccordion')' 
          
    to the build.gradle file inside the app folder. And modify the settings.gradle file in your project as 
            
           include ':app', ':easyaccordion'

           project (':easyaccordion').projectDir = new File('/path/to/the/folder/easyaccordion')
           
    OR add (to your app module's build.gradle)
    
          compile 'com.riyagayasen.android:easyaccordion:1.0.3'  

  3. Now that you have added the project in your app, it is time to use it. 

You can generate the accordion component either from the java class or from the XML

The accordion component can also be generated from the java class 
        
         AccordionView accordionView = new AccordionView(this);

The best way to use this component is from the layout xml file. The following example illustrates the use:

      <com.riyagayasen.easyaccordion.AccordionView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentStart="true"
        android:visibility="visible"
        app:isAnimated="false"
        app:heading="This is a demo accordion"
        app:isExpanded="true"
        app:isPartitioned="true">

        <TextView
            android:id="@+id/textView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Demo accordion text" />

        <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Test Button"
            android:id="@+id/button_2"
            android:layout_below="@+id/textView" />
        <Button
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Test Button 2"
            android:layout_below="@+id/button_2" />


    </com.riyagayasen.easyaccordion.AccordionView>
The 'app' namespace can be defined as 
     
     xmlns:app="http://schemas.android.com/apk/res-auto"

This generates the layout as 

![Alt text](/screenshot.png?raw=true "Optional Title")

The accordion component has two parts. 
  1. Heading: the top part of the accordion, 'This is a demo accordion' in the above example. 
  2. The body, that I call 'paragraph'. This body is a RelativeLayout and would contain what ever UI elements you add to the accordion. 
  
You would see that there are several new attributes I have defined for the accordion. 
  1. Heading 
  
         app:heading="This is a demo accordion"
   This defines the string to be used as heading. 
  
  2. Partition (boolean): the 'line' between the heading and the paragraph. 
  
        app:isPartitioned="true"
      
      OR
        
        accordionView.setPartitioned(true);
    
    This  value determines if the line is drawn between the heading and the paragraph. 
  
  3. Expanded (boolean): this value determines if the paragraph is expanded by default. 
  
        app:isExpanded="true"
        
       OR
       
        accordionView.setExpanded(true);
  
  4. Animated (boolean): this value determines if the accordion expands or collapses with an animation
        
        app:isAnimated="true"
      
       OR
       
        accordionView.setAnimated(true);
        
      The accordion view above is an AccordionView object that can be created as (in an Activity):
        
        AccordionView wordView = new AccordionView(this);
  
  5. Heading Background: this value determines the color/drawable to be used for the background
      
        app:headingBackground = "@drawable/example_layout_drawable" (to use a drawable)
        app:headingBackgroundColor = "@android:color/white" (to set just the color)
       
       OR
        
        accordionView.setHeadingBackground(R.drawable.custom_background);    (to use a drawable)
        accordionView.setHeadingBackgroundColor(Color.WHITE);    (to set just the color)
        
  6. Body/Paragraph Background: this value determines the color/drawable to be used for the body of the accordion
        
        app:bodyBackground="@drawable/custom_drawable" (to set the drawable)
        app:bodyBackgroundColor="#233245" (to set the color)
        
       OR
       
        accordionView.setBodyBackground(R.drawable.custom_drawable);    (to set just the color)
        accordionView.setBodyBackgroundColor(Color.CYAN);    (to set just the color)
      
      
 
To add different elements into the body (paragraph) of the accordion component, you can simply define them within the AccordionView tag. 

You can also add a listener to be triggered when the accordion is expanded or collapsed

        accordionView.setOnExpandCollapseListener(new AccordionExpansionCollapseListener() {
                    @Override
                    public void onExpanded(AccordionView view) {
                        //your code here
                    }

                    @Override
                    public void onCollapsed(AccordionView view) {
                       //you code here
                    }
                });

In case of any questions, kindly email me at riyagayasen@gmail.com 
