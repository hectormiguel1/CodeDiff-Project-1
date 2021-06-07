Java Data Structure Tutorial Project
You will be given a Data Structure to research. You goal is to create a tutorial for that data structure. For a reference, checkout FreeCodeCamp.

Step 1: Research
Research about your Data Structure. You should know:

How it works/stores data
How a client interacts with it (interface)
The pros and cons of using the data structure
The space and time complexity of its behavior
# Box Data Structure

The Box Data stucture works by holding whatever information you want and allowing you to stick the box in either the attic or basement. A client can use the `pack()` and `unpack()` methods to put data into and remove from the box. They can also use the `store()` and `unstore()` methods to store it in or remove it from they room they are storing it in. The box provides the benefit of combining like items but has the disadvantage of taking up space and increase the risk of forgeting what is in the box. All the actions run in constant time.
Tutorial
Write a turotial that another developer could follow. We recommend the following structure:

Show how to set up the required classes and stub all the method

class Box<T> {

    private List<T> stuff;
    private boolean isStored;

    public void pack(T t){}
    public T[] unpack(){return null;}
    public void store(){}
    public void unstore(){}

}
One method at a time, provide a decription and implement.

// Pack allows us to add more things into the box so long as the box is not currently stored
public void pack(T t){
    if(!this.isStored){
        this.stuff.add(t);
    }
}
Demonstrate the usage of the Data Structure with a few examples

Box<String> summerClothes = new Box<>();
summerClothes.pack("Swimming Trunks");
summerClothes.pack("Bucket Hat");
summerClothes.store();
summerClothes.pack("Flippy Floppies"); // Can't add because the box has already been stored!!!
You can build your tutorial with the tool of your choosing so long as you can show text, code, images, etc. We reccomend using a markdown file but a website or Word document will also work.

Presentation
You will give a short presintation to the rest of the class. Here you will explain all about your Data Structure and provide a demonstration of why and how to use it.
