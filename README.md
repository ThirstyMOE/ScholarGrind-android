# ScholarGrind-android

### Project Description
This is an app designed to take pictures of physical and digital notes from college lectures and store them in a specific directory that won't interfere with Android's default photo gallery. Later, the user (a college student like me) can view those notes through the app's implementation for viewing photos. Ideally, the user can use this app without needing wifi or data access, like on long commutes by metro system.

### Process through Development
This is my second Android project that was designed as a slightly deeper dive into Android app programming and object oriented programming.
My motivation was to both make a proof of concept for a streamlined note collecting app, and to figure out how to use some more of Android's systems like FileProviders and using internal storage. A more general purpose was also to give myself another chance to go through the process of creating a project from idea to design to implementation.

### Initial Design Process
The basic idea I had for the project was to make an app streamlined for viewing notes from my various classes. This design was my first attempt at wireframe designing an app, where I mostly tried to simplify the navigation to be short and linear. I designed in an Open and New branch of screens, using the minimal amount of button clicks necessary. The idea came out mostly well during implementation, although there were some problems along the way.

### Implementation Process
This was one of my first personal project where I tried to extensively use object oriented programming style, but I ended up with awkward solutions in order to slightly maintain encapsulation.

#### New Note Creation
I am still learning how to best transfer data across different activities in Android. In the event of backtracking away from the camera activity without taking a picture and accepting it, I didn't know where was the best place to create a file object in order to store the picture into internal storage. Definitely in a next run through I would find a way to make some static system that the app could use to access FileProviders and internal storage.

###### File System Access
This was my first encounter with dealing with FileProviders in Android. I did find that the way I implemented FileProviders into my app was very much redundant due to me using it in multiple different places, while still having the same use cases.

#### Note Opening System

###### File Viewing
My process was strange in approaching file viewing, because rather than using ACTION_VIEW intents (as I did later), I decided to use ImageViews to display my photos. It worked out to some extent, but I figured out later that trying to design a photo viewing app inside this app would cost lots of time and also probably end up buggy and unusable. I decided to cut my losses there and started using a default image viewing activity for viewing files.

#### Custom ArrayAdapters
After using a basic ListView with TextViews for ArrayAdapter layout, I decided to explore creating an ArrayAdapter based around using a layout with many different elements attached to each item in the ListView. Through some searching I was able to figure out that subclassing an ArrayAdapter and giving it a custom XML layout is very doable, so I opted for that, where currently, although it is still essentially a basic ArrayAdapter, I can later add buttons and image previews to the ListView for added usability.

### Conclusion
The project was implemented without as much forethought as I should have, but it is still usable to some extent, and revealed a lot of the kinds of approaches I should try when using object oriented programming. The project also revealed more on how to work within Android's API using object like FileProviders and ArrayAdapters. Overall, I found the project to be a great learning experience, despite lacking in the implementation quality.

### Next Steps
In continuing the same app, I would continue with the ArrayAdapter in order to get essentially a file browser with delete and image preview functions for each ListView item. Or as an alternative, I might be able to find a default file browser activity to cover that as well.

If continuing further, I find it would help to restart and build a stronger foundation in the object oriented programming flow, so that I might have a better chance of avoiding the same mistakes I made earlier.
