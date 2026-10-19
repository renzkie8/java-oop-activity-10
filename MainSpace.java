public class MainSpace{
    private static Scanner space = new Scanner(System.in);

    private static Mission[] missions = new Mission[100];
    private static Spacecraft[] spacecrafts = new Spacecraft[100];
    private static Resource[] resources = new Resource[100];
    private static ResearchProject[] researchProj = new ResearchProject[100];
    private static int spacecraftCount = 0;
    private static int resourceCount = 0;
    private static int missionCount = 0;
    private static int researchCount = 0;

    private static String userRole;

    public static void manageMissionsMenu(){
        while (true) {
            System.out.println("\n==== Manage Missions ====\n");
            System.out.println("1. Create New Mission");
            System.out.println("2. Add/Remove Assigned Spacecraft");
            System.out.println("3. Add/Remove Assigned Resources");
            System.out.println("4. Display Missions");
            System.out.println("5. End A Mission");

            if (userRole.equals("Admin")){
                System.out.println("6. Back to Admin Menu");
            } else{
                System.out.println("6. Exit");
            }

            System.out.print("\nEnter choice number (1-5): ");
            int missionChoice = space.nextInt();
            space.nextLine();

            switch (missionChoice) {
                case 1:
                    createMission();
                    break;

                case 2:
                    modifyAssignedSpacecraft();
                    break;

                case 3:
                    modifyAssignedResources();
                    break;

                case 4:
                    displayAllMissions();
                    break;

                case 5:
                    missionEnded();
                    break;

                case 6:
                    if (userRole.equals("Admin")){
                        return;
                    } else{
						progExit();
                    }

                default:
                    System.out.println("Invalid input. Please enter an integer from 1 to 5 only.");
            }
        }
    }

    /*MainSpace is able to access and modify the data
    encapsulated in Mission class through the setter and public methods
    contained in said class.
    */

    public static void createMission(){
        System.out.print("\nEnter NAME of Mission: ");
        String missionName = space.nextLine();

        System.out.print("Enter TYPE of Mission (Search, Investigation, Maintenance, etc.): ");
        String missionType = space.nextLine();

        System.out.print("Enter ASSIGNED STAFF: ");
        String missionStaff = space.nextLine();

        System.out.print("Enter MISSION OBJECTIVE/s: ");
        String missionObjective = space.nextLine();

        System.out.print("Enter DURATION of mission (in days): ");
        int missionDuration = space.nextInt();
        space.nextLine();

        Mission newMission = new Mission(missionName, missionStaff, missionType, missionObjective, missionDuration);

        System.out.println("\nASSIGN SPACECRAFT\n");

        System.out.println("Available Spacecraft/s: \n");
        boolean foundAvailableSpacecraft = false;
        for (int i = 0; i < spacecraftCount; i++){
            if (spacecrafts[i].isAvailable()){
                System.out.println((i+1) + "." + spacecrafts[i].getName().toUpperCase());
                foundAvailableSpacecraft = true;
            }
        }

        if (!foundAvailableSpacecraft){
            System.out.println("No available spacecraft. Please add spacecrafts in Manage Spacecraft menu first.");
        } else{
            System.out.print("\nEnter spacecraft number to assign: ");
            int spacecraftChoice = space.nextInt();
            space.nextLine();

            if (spacecraftChoice >= 1 && spacecraftChoice <=spacecraftCount && spacecrafts[spacecraftChoice - 1].isAvailable()){
                newMission.assignSpacecraft(spacecrafts[spacecraftChoice - 1]);
                System.out.println("\nSpacecraft " + spacecrafts[spacecraftChoice - 1].getName().toUpperCase() + " successfully assigned.\n");
            } else{
                System.out.println("Invalid input or spacecraft unavailable.");
            }
        }

        System.out.println("\nASSIGN RESOURCES");

        System.out.println("\nAvailable Resource/s: \n");
        boolean foundAvailableResources = false;
        for (int i = 0; i < resourceCount; i++){
            if (resources[i].getQuantity() > 0){
                System.out.println((i+1) + "." + resources[i].getName().toUpperCase() + " | " + resources[i].getQuantity());
                foundAvailableResources = true;
            }
        }
        if (!foundAvailableResources){
            System.out.println("No available resource/s. Please add spacecrafts in Manage Resource menu first.");
        } else{
            System.out.print("\nEnter resource name: ");
            String resourceName = space.nextLine();
            System.out.print("Enter quantity: ");
            int resourceQuantity = space.nextInt();
            space.nextLine();

            boolean resourceAssigned = false;
            for (int i = 0; i < resourceCount; i++){
                if (resources[i].getName().equalsIgnoreCase(resourceName) && resources[i].getQuantity() >= resourceQuantity){
                    Resource assignedResource = new Resource(resources[i].getName(), resourceQuantity);
                    newMission.addResource(assignedResource);
                    resources[i].decrementQuantity(resourceQuantity);
                    System.out.println("\nResource " + resourceName.toUpperCase() + " | Quantity: " + resourceQuantity + " succesfully added to mission.");
                    resourceAssigned = true;
                    break;
                }
            }
            if (!resourceAssigned){
                System.out.println("Resource not found or insufficient quantity.");
            }
        }

        missions[missionCount++] = newMission;
        System.out.println("\nMission " + missionName.toUpperCase() + " created successfully."); //retrieves and format name from Mission class
    }

    public static void modifyAssignedSpacecraft(){
        System.out.print("\nEnter mission name to modify: ");
        String missionName = space.nextLine();

        for(int i = 0; i < missionCount; i++){
            if(missions[i].getName().equalsIgnoreCase(missionName)){

                //spacecraft name accessible thru getAssignedSpacecraft() in Mission class
                System.out.println("\nCurrent Assigned Spacecraft: " + (missions[i].getAssignedSpacecraft() != null ? missions[i].getAssignedSpacecraft().getName() : "None"));

                System.out.println("\n1. Remove Assigned Spacecraft");
                System.out.println("2. Add New Spacecraft");

                System.out.print("\nEnter choice number (1-2): ");
                int choice = space.nextInt();

                switch (choice){
                    case 1:
                        if (spacecraftCount != 0){
                            missions[i].removeSpacecraft();
                            System.out.println("Assigned spacecraft removed.");
                        } else{
                            System.out.println("No spacecraft to be removed.");
                        }
                        break;

                    case 2:
                        System.out.println("\nAvailable Spacecraft/s:\n");
                        boolean foundAvailableSpacecraft = false;
                        for (int j = 0; j < spacecraftCount; j++) {
                            if (spacecrafts[j].isAvailable()) {
                                System.out.println((j + 1) + ". " + spacecrafts[j].getName());
                                foundAvailableSpacecraft = true;
                            }
                        }

                        if (!foundAvailableSpacecraft) {
                            System.out.println("No available spacecraft. Please add spacecrafts in Manage Spacecraft menu first.");
                        } else {
                            System.out.print("\nEnter spacecraft number to assign: ");
                            int spacecraftChoice = space.nextInt();
                            space.nextLine();

                            if (spacecraftChoice >= 1 && spacecraftChoice <= spacecraftCount && spacecrafts[spacecraftChoice - 1].isAvailable()) {
                                missions[i].assignSpacecraft(spacecrafts[spacecraftChoice - 1]);
                                System.out.println("\nNew spacecraft assigned.");
                            } else {
                                System.out.println("\nInvalid choice or spacecraft not available.");
                            }
                        }
                        break;
                    default:
                        System.out.println("\nInvalid input. Please enter either 1 or 2.");
                }
                return;
            }
        }
        System.out.println("\nMission not found.");
    }

    public static void modifyAssignedResources(){
        System.out.print("\nEnter mission name to modify: ");
        String missionName = space.nextLine();

        for (int i = 0; i < missionCount; i++){
            if (missions[i].getName().equalsIgnoreCase(missionName)){

                ////resource name acessed thru getAssignedResources() in Mission class
                System.out.println("\nCurrent Assigned Resources:" + (missions[i].getAssignedResources()));

                System.out.println("\n1. Remove Assigned Resource");
                System.out.println("2. Add New Resource");

                System.out.print("\nEnter choice number (1-2): ");
                int choice = space.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("\nEnter resource name to remove: ");
                        String resourceNameRemove = space.nextLine();
                        missions[i].removeResource(resourceNameRemove);
                        System.out.println("Resource " + resourceNameRemove.toUpperCase() + "removed.");
                        break;

                    case 2:
                        System.out.println("\nAvailable Resources:");
                        boolean foundAvailableResources = false;
                        for (int j = 0; j < resourceCount; j++){
                            if (resources[j].getQuantity() > 0){
                                System.out.println((j + 1) + ". " + resources[j].getName().toUpperCase() + " | Quantity: " + resources[j].getQuantity());
                                foundAvailableResources = true;
                            }
                        }

                        if (!foundAvailableResources){
                            System.out.println("\nNo available resources. Please add resources in Manage Resources menu first.");
                        } else {
                            System.out.print("\nEnter resource NAME to add: ");
                            String resourceNameAdd = space.nextLine();
                            space.nextLine();

                            System.out.print("Enter QUANTITY to add: ");
                            int quantity = space.nextInt();

                            boolean resourceAssigned = false;
                            for (int j = 0; j < resourceCount; j++){
                                if (resources[j].getName().equalsIgnoreCase(resourceNameAdd) && resources[j].getQuantity() >= quantity) {

                                    //object
                                    Resource assignedResource = new Resource(resources[j].getName(), quantity);
                                    missions[i].addResource(assignedResource);
                                    resources[j].decrementQuantity(quantity);
                                    resourceAssigned = true;
                                    System.out.println("\nResource " + resourceNameAdd.toUpperCase() + "| Quantity: " + quantity + " added to mission.");
                                    break;
                                }
                            }

                            if (!resourceAssigned){
                                System.out.println("\nResource not found or insufficient quantity.");
                            }
                        }
                        break;
                    default:
                    System.out.println("Invalid input. Please enter either 1 or 2.");
                }
                return;
            }
        }
        System.out.println("\nMission not found.");
    }

    //this method access Mission class' private details (name, type, duration, etc.)
    public static void displayAllMissions(){
        System.out.println("\n==== Missions Log ====\n");
        if (missionCount > 0){
            for (int i = 0; i < missionCount; i++){
                missions[i].displayDetails();
                System.out.println("--------------------------------------");
            }
        } else{
            System.out.println("Missions List Empty.");
        }
    }

    //uses Mission class attributes thru endMission()
    public static void missionEnded(){
        System.out.print("\nEnter mission name to end: ");
        String missionName = space.nextLine();
        boolean missionFound = false;
        for (int i = 0; i < missionCount; i++){
            if (missions[i].getName().equalsIgnoreCase(missionName)){
                missionFound = true;
                System.out.print("\nWould you like to end Mission " + missionName.toUpperCase() + "? (y/n): ");
                String choice = space.nextLine();
                if (choice.equalsIgnoreCase("y")){
                    missions[i].endMission();
                    System.out.println("\nMission " + missionName + " has been ended.");
                    for (int j = i; j < missionCount - 1; j++){
                        missions[j] = missions[j + 1];
                    }
                    missions[missionCount - 1] = null;
                    missionCount--;
                    System.out.println("Mission " + missionName + " has been removed from the list.");
                } else{
                    System.out.println("Mission " + missionName + " will not be ended.");
                }
                break;
            }
        }
        if (!missionFound) {
            System.out.println("Mission not found.");
        }
    }
}

/*MainSpace can interact (access and modify) with Mission only through its public methods.
This way, encapsulation is preserved and private information security is ensured.
*/