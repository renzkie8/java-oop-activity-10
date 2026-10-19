package com.example.mainspacers;

//Mission class exhibits Encapsulation
class Mission{

    /*variables are declared as private,
    meaning access or modification is restricted
    outside this Mission class; they are encapsulated.
    */

    private String name;
    private String staff;
    private String type;
    private String objective;
    private int duration;
    private Spacecraft assignedSpacecraft;
    private Resource[] assignedResources = new Resource[100];
    private int resourceCount = 0;

    //the following public methods allow controlled access its private data
    public Mission(String missionName, String missionStaff, String missionType, String missionObjective, int missionDuration){
        this.name = missionName;
        this.staff = missionStaff;
        this.type = missionType;
        this.objective = missionObjective;
        this.duration = missionDuration;
    }

    /*
    Likewise, the Spacecraft and Resource classes
    allow Mission class to access their data through the same logic.
    */
    public void assignSpacecraft(Spacecraft spacecraft){
        assignedSpacecraft = spacecraft;       //setters in public methods makes it usable in MainSpace
        spacecraft.setAvailability(false);
    }

    public void removeSpacecraft(){
        if (assignedSpacecraft != null){
            assignedSpacecraft.setAvailability(true);
            assignedSpacecraft = null;
        } else{
            System.out.println("No assigned spacecraft to remove.");
        }
    }

    public void addResource(Resource resource){
        if (resourceCount < assignedResources.length){
            if (resource.getQuantity() > 0){
                assignedResources[resourceCount++] = resource;
            }
        } else{
            System.out.println("maximum number of resources reached.");
        }
    }

    public void removeResource(String resourceName){
        for (int i = 0; i < resourceCount; i++){
            if (assignedResources[i].getName().equalsIgnoreCase(resourceName)) {
                assignedResources[i] = assignedResources[--resourceCount];
                assignedResources[resourceCount] = null;
                System.out.println("Resource " + resourceName.toUpperCase() + " removed.");
                return;
            }
        }
        System.out.println("Resource not found.");
    }

    public void endMission(){
        System.out.println("Ending the mission: " + name + "...");
        resourceCount = 0;
        if (assignedSpacecraft != null){
            assignedSpacecraft.setAvailability(true);
            assignedSpacecraft = null;
        }
    }

    public String getName(){  //getter methods give MainSpcae read-only access to encapsulated data
        return name;
    }

    public Spacecraft getAssignedSpacecraft(){
        return assignedSpacecraft;
    }

    public Resource[] getAssignedResources(){
        return assignedResources;
    }

    public int getResourceCount(){
        return resourceCount;
    }

    public void displayDetails(){
        System.out.println("Mission Name: " + name.toUpperCase());
        System.out.println("Assigned Staff: " + staff.toUpperCase());
        System.out.println("Mission Type: " + type.toUpperCase());
        System.out.println("Mission Objective: " + objective.toUpperCase());
        System.out.println("Mission Duration: " + duration + " days");
        if (assignedSpacecraft != null){
            System.out.println("Assigned Spacecraft: " + assignedSpacecraft.getName().toUpperCase() + " | Status: " + assignedSpacecraft.getStatus());
        } else{
            System.out.println("No spacecraft assigned.");
        }
        System.out.println("Assigned Resources: ");
        if (resourceCount == 0){
            System.out.println("No resources assigned.");
        } else{
            for (int i = 0; i < resourceCount; i++){
                System.out.println(i+1 + ". "+ assignedResources[i].getName().toUpperCase() + " | Quantity: " + assignedResources[i].getQuantity());
            }
        }
        System.out.println();
    }
}