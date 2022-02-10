# swinglib
Utility code for Java swing JPanel classes

### Let the developer focus on the actual code instead of having to remake a UI for each project with the tedious swing API.

### - [Documentation](https://milind-u.github.io/swinglib)
### - [Example](#example)
### - [Setup](#setup)
### - [Help](#help)

## Example
The [example UI](Example.java) demonstrates typical usage of swinglib.

In less than 45 lines of code, it looks like this:

![](https://storage.googleapis.com/html_files_bucket/example.png)

## Setup
- Download the swinglib code from github [here](https://github.com/milind-u/swinglib/archive/refs/heads/main.zip)
- Extract the zip file into a folder called swinglib
- Put the swinglib folder in your source code folder
  - Ex. if you had a project folder `my_project`, this would be located in `my_project/swinglib`
- Create a `Screen` class that extends `AbstractScreen` just like in the [example](Example.java)
- Import `AbstractScreen` with 
```java 
import swinglib.AbstractScreen
```
- You can import other swinglib classes like that
- Create a `Runner` class with a `main` that creates and runs a `Screen`, just like the `main` in the [example](Example.java) 

## Help
For help with swinglib, [submit an issue](https://github.com/milind-u/swinglib/issues) with your problem

To file a bug, either [submit an issue](https://github.com/milind-u/swinglib/issues), or if you know how to fix it submit a [pull request](https://github.com/milind-u/swinglib/pulls)

