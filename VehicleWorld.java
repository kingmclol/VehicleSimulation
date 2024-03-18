import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Collections;
import java.util.ArrayList;
/**
 * Freeman Wang ICS4U
 * <=================-STORY-===================>
 * This Vehicle Simulation is set in a city that is currently experiencing a zombie apocalypse! Obviously,
 * everything is in chaos, so those who can want to leave as soon as possible (moving to the right) whilst those who are
 * unlucky are stuck running around on the roads.
 * 
 * Different Humans work together (along with those driving the Vehicles) to try to survive this zombie outbreak.
 * <=================-PEDESTRIANS-==================>
 * There are 4 pedestrian types in total; split into Humans and Zombies.
 * Zombies simply try to bite and infect any Humans nearby.
 * 
 * Humans are split into 3 subclasses:
 * 1. Civilians, who simply move around the city with no notable abilities,
 * 2. Medics, who can revive any downed Humans (and possibly save them from infection),
 * 3. Soldiers, who protect any Humans around by shooting Zombies,
 * Any Human who is bit by a Zombie will turn into one, if not treated within a time limit.
 * <=================-VEHICLES-==================>
 * There are 4 Vehicle types in total, all with different interactions between Pedestrians:
 * 1. Cars, which drive quickly and run over Humans and Zombies alike,
 * 2. Ambulances, which heal downed Humans and cures Zombies on contact,
 * 3. Buses, which pick up any Civilians that get on from the sides, bringing them to safety,
 * 4. Trucks, which carry explosives and will EXPLODE on contact with any Pedestrian (downed or not).
 * <=================-EVENTS-==================>
 * The primary *Local Effect* is the Explosions from the Bombs and Explosive Trucks.
 * The primary *World Effect* is when it becomes night. Zombies see farther and move faster while Humans see less.
 * 
 * Additionally, there are other "Events" which may randomly occur, to make the simulation more interesting:
 * 1. Carpet Bombing. A plane will spawn in and drop bombs randomly throughout the city.
 * 2. Traffic Rush. Maximizes the spawn rate for Vehicles for a short moment.
 * 3. Zombie Rush. Spawns a large wave of Zombies in a short time span.
 * <=================-KNOWN BUGS-==================>
 * --I guess you need Java 8 at a minimum for Lambda support. Probably not an issue.
 * --There is a bug when the city ambience sound file begins to loop, which results in an
 *   IndexOutOfBounds Exception. It should be caused by either metadata in the sound file (which I cleared already),
 *   or a corruption in the file itself. It should be fixed now.
 * --There will be stutters/performance drops when Ambulances interact with Pedestrians, likely because the Pedestrian it is healing
 *   is also getting knocked down at the same time. Thus, any sounds may play in a short moment, which causes stuttering. The
 *   effect should be minimized now.
 * --Some Pedestrians would go ZOOOOM when they're sandwitched between two Vehicles with tall sprites (such as the Bus
 *   or the Ambulance). This is likely because of how Vehicles handle repelling Pedestrians... which I don't want to modify,
 *   as my changes would probably cause more issues without proper understanding on how it works.
 * <=================-CREDITS & ATTRIBUTION-==================>
 * Bombing Plane sprite from Adobe Stock
 * Background image from iopwiki.com
 * Most other images are Greenfoot default assets, some painted over by me.
 * 
 * Tornado Siren II by Delilah from SoundBible.com
 * Vehicle Alarm by nps.gov from SoundBible.com
 * Crickets Chirping by Lisa Redfern from SoundBible.com
 * Male Grunt by anonymous from SoundBible.com
 * Car Horn Honk 1 by anonymous from SoundBible.com
 * Horn Honk by Mike Koenig from SoundBible.com
 * 
 * Sounds from ZapSplat.com:
 * Big and heavy dynamite explosion 1 
 * Big and heavy dynamite explosion 2
 * Big and heavy dynamite explosion 3
 * City ambience, busy road, cars, trucks, and motercycles passing, police siren, horn honks, occasional people, Mekong Delta, Vietnam 1
 * Game sound, generic musical beep 2
 * Gore bite, flesh rip with mouth
 * One shot, electric, electricity, Lofi, Game Audio, Action, UI, HUD 3
 * 
 * SuperDisplayLabel by Mr.Cohen
 */
public class VehicleWorld extends World
{
    private GreenfootImage background;

    // Color Constants
    public static Color GREY_BORDER = new Color (108, 108, 108);
    public static Color GREY_STREET = new Color (88, 88, 88);
    public static Color YELLOW_LINE = new Color (255, 216, 0);

    public static boolean SHOW_SPAWNERS = true;
    
    // Set Y Positions for Pedestrians to spawn
    public static final int TOP_SPAWN = 190; // Pedestrians who spawn on top
    public static final int BOTTOM_SPAWN = 705; // Pedestrians who spawn on the bottom
    
    // Self explanatory, but they're number of pedestrian and vehicle types
    private static final int NUM_PEDESTRIAN_TYPES = 4;
    private static final int NUM_VEHICLE_TYPES = 4;
    
    // Instance variables / Objects
    private boolean twoWayTraffic, splitAtCenter;
    private int laneHeight, laneCount, spaceBetweenLanes;
    private int[] lanePositionsY;
    private VehicleSpawner[] laneSpawners;
    
    // Variables related to events.
    private boolean daytime;
    private DarkFilter nightFilter;
    private boolean alwaysSpawnVehicles;
    private boolean airRaid;
    
    // Varables related to managing pedestrian stats.
    // Index assignment as follows:
    // 0 -> Zombies
    // 1 -> Civilians
    // 2 -> Medics
    // 3 -> Soldiers
    private int[] pStats;
    private int count;
    private static final boolean SHOW_STATS = true; // Enable showing stats about the simulation.
    private static final boolean SHOW_AS_PERCENTAGE = true; // Only used for printing in terminal.
    private SuperDisplayLabel statsBar;

    private static final boolean TEST_LANE_CHANGE = false; // If true, spawns many vehicles, always.
    
    // Ambience and raid sounds
    private static SuperSound raidSound = new SuperSound("Air Raid Warning.mp3", 1, 50);
    private static SuperSound cityAmbience = new SuperSound("City Ambience.mp3", 1, 40);
    /**
     * Constructor for objects of class MyWorld.
     * 
     * Note that the Constrcutor for the default world is always called
     * when you click the reset button in the Greenfoot scenario screen -
     * this is is basically the code that runs when the program start.
     * Anything that should be done FIRST should go here.
     * 
     */
    public VehicleWorld()
    {    
        // Create a new world with 1024x800 pixels, UNBOUNDED
        super(1024, 800, 1, false); 
        addObject(new WorldEventManager(), 0 ,0);
        nightFilter = new DarkFilter(this);
        daytime = true;
        alwaysSpawnVehicles = false;
        airRaid = false;
        // initialize the stats array.
        pStats = new int[4];
        count = 0; // for UI refreshing
        if (SHOW_STATS) {
            // Create String array for SuperDisplayLabel, initialize, add to world
            String[] labels = new String[] {
                "  ZOMS: ",
                "  CIVS: ",
                "  MEDS: ",
                "  SDRS: "
            };
            statsBar = new SuperDisplayLabel();
            statsBar.setLabels(labels);
            addObject(statsBar, getWidth()/2, 0);
        }
        // This command (from Greenfoot World API) sets the order in which 
        // objects will be displayed. In this example, Pedestrians will
        // always be on top of everything else, then Vehicles (of all
        // sub class types) and after that, all other classes not listed
        // will be displayed in random order. 
        
        // SuperDisplayLable has highest priority since it is UI
        // Explosions have second highest priority, unaffected by the DarkFilter since 
        // They are technically light sources
        // AnnounceText has third priority, as they act as UI
        // DarkFilter has fourth highest priority to avoid z-sort affecting them
        setPaintOrder(SuperDisplayLabel.class, ExplosionParticle.class, AnnounceText.class, Effect.class);

        // set up background -- If you change this, make 100% sure
        // that your chosen image is the same size as the World
        background = new GreenfootImage ("gameBackground2_expanded_v2.jpg");
        setBackground (background);

        // Set critical variables - will affect lane drawing
        laneCount = 8;
        laneHeight = 48;
        spaceBetweenLanes = 6;
        splitAtCenter = false;
        twoWayTraffic = false;

        // Init lane spawner objects 
        laneSpawners = new VehicleSpawner[laneCount];

        // Prepare lanes method - draws the lanes
        lanePositionsY = prepareLanes (this, background, laneSpawners, 232, laneHeight, laneCount, spaceBetweenLanes, twoWayTraffic, splitAtCenter);

        laneSpawners[0].setSpeedModifier(0.8);
        laneSpawners[3].setSpeedModifier(1.4);

        setBackground (background);
        
        Greenfoot.setSpeed(50);
    }
    public void started() {
        if (airRaid) raidSound.play();
        cityAmbience.playLoop();
        if (!daytime) Nighttime.resumeAmbience();
    }
    public void stopped() {
        raidSound.pause();
        cityAmbience.pause();
        if (!daytime) Nighttime.pauseAmbience();
    }
    public void act () {
        super.act();
        spawn();
        zSort ((ArrayList<Actor>)(getObjects(Actor.class)), this);
        if (SHOW_STATS) {
            if (count++ >= 10) {
                count = 0;
                statsBar.update(pStats);
                
                // Show more detailed statistics of the simluation in the terminal.
                //printStats(SHOW_AS_PERCENTAGE);
            }
        }
    }
    // /**
     // * Progresses the time of the simulation between Daytime and Nighttime.
     // * DEPRECATED.
     // */
    // public void progressDayCycle(){
        // daytime = !daytime; // toggle world time.
        // // System.out.println(daytime ? "DAY" : "NIGHT");
        
        // // Change all Pedestrians to their respective stats.
        // ArrayList<Pedestrian> pedestrians = (ArrayList<Pedestrian>)getObjects(Pedestrian.class);
        // for (Pedestrian p : pedestrians) p.setStats(daytime);
        
        // // Add the effect that dims/undims the screen.
        // if (!daytime){ // nightime. Add the filter.
            // nightFilter.timeToExist(); // Tells the nightFilter that it should prepare to make itself appear.
            // addObject(nightFilter, getWidth()/2, getHeight()/2); // Add it to the World.
        // }
        // else { // Day. remove the filter.
            // nightFilter.timeToRemove();
        // }
    // }
    public void setTime(boolean daytime){
        this.daytime = daytime;
    }
    /**
     * Prints out the pStats in a readable manner for debugging purposes, or you just want to read it
     * from the terminal. Not meant to be used normally.
     * @param asPercentage Whether to print out the stats as relative frequencies rather than count.
     */
    private void printStats(boolean asPercentage){
        // In before i get nuked for unreadable code
        if (asPercentage) {
            int total = 0;
            for (int num : pStats) total+=num;
            // This does not get / by zero, possibiliy because I cast to doubles?
            // Either way, it works. Somehow. And if it ain't broken, don't fix it.
            System.out.println(String.join(" | ",
                                    "Zoms: " + Utility.roundToPrecision((double)pStats[0]/total*100, 2) + "%",
                                    "Civs: " + Utility.roundToPrecision((double)pStats[1]/total*100, 2) + "%",
                                    "Meds: " + Utility.roundToPrecision((double)pStats[2]/total*100, 2) + "%",
                                    "Sdrs: " + Utility.roundToPrecision((double)pStats[3]/total*100, 2) + "%"
                                    ));
        }
        else {
            System.out.println(String.join(" | ",
                                    "Zoms: " + pStats[0],
                                    "Civs: " + pStats[1],
                                    "Meds: " + pStats[2],
                                    "Sdrs: " + pStats[3]
                                    ));
        }
    }
    /**
     * Decreases the count of the given Pedestrian by 1.
     * @param a The actor to consider.
     */
    public void removeFromCount(Actor a){
        int index = convertActorToIndex(a);
        pStats[index] = Math.max(0, pStats[index]-1); // restrict to lowest as 0, just to be safe
    }
    /**
     * Increases the count of the given Pedestrian by 1.
     * @param a The actor to consider in pStats.
     */
    public void addToCount(Actor a){
        int index = convertActorToIndex(a);
        pStats[index]+=1;
    }
    /**
     * Converts the given Actor to its proper index for pStats based on its type.
     * @param  a the Actor to analyze.
     * @return 0 if the Actor is a Zombie. 
     *         1 if the Actor is a Civilian.
     *         2 if the Actor is a Medic.
     *         3 if the Actor is a Soldier.
     */
    private int convertActorToIndex(Actor a){
        if (a instanceof Zombie){
            return 0;
        }
        else if (a instanceof Civilian){
            return 1;
        }
        else if (a instanceof Medic) {
            return 2;
        }
        else if (a instanceof Soldier) {
            return 3;
        }
        else {
            System.out.println("Given invalid actor for this use case.");
            return -1; // give negative index to induce error. Obviously, it's user error.
        }
    }
    /**
     * Starts a carpet bombing event
     */
    public void startBombing(){
        airRaid = true;
        raidSound.play();
        // Spawn the plane slightly later.
        createEvent(new DelayedEvent(() -> addObject(new BomberPlane(), 0, 80), 120));
    }
    /**
     * Stops carpet bombing event (no more siren)
     */
    public void stopBombing(){
        airRaid = false;
        raidSound.stop();
    }
    /**
     * Spawns a wave of Zombies at the top or bottom spawn.
     */
    public void spawnZombieWave() {
        boolean spawnAtTop = Greenfoot.getRandomNumber(2) == 0 ? true : false;
        int ySpawnLocation = spawnAtTop ? TOP_SPAWN:BOTTOM_SPAWN;
        int direction = spawnAtTop ? 1:-1;
        
        // Add some zombies with staggered spawns to limit frame drops. Random X value for each.
        createEvent(new RepeatingEvent(()->addObject(new Zombie(direction), Greenfoot.getRandomNumber(600)+100, ySpawnLocation),
                        10, // 10 act delay between spawns.
                        20)); // add zombies 20 times.
    }
    /**
     * Makes the world spawn many vehicles for 180 acts. Why am I even commenting these?
     */
    public void spawnManyVehicles() {
        // Make it so spawnVehicle() will always run. Note that this will not spam vehicles, as
        // they won't spawn if another Vehicle is already touching the LaneSpawner.
        alwaysSpawnVehicles = true;
        
        // After 120 acts, undo the action.
        createEvent(new DelayedEvent(() -> alwaysSpawnVehicles = false, 180)); 
    }
    /**
     * Spawns the given Event at 0,0.
     */
    private void createEvent(Event e){
        addObject(e,0,0);
    }
    /**
     * Returns a cloned Array of lanePositionsY. Unused after finding about
     * getLaneY() lol
     */
    public int[] getLanePositionsY(){
        return lanePositionsY.clone();
    }
    /**
     * Returns whether it is currently daytime.
     */
    public boolean isDaytime(){
        return daytime;
    }
    private void spawn () {
        // Chance to spawn a vehicle
        if (Greenfoot.getRandomNumber (laneCount * 8) == 0 || TEST_LANE_CHANGE || alwaysSpawnVehicles){
            spawnVehicle();
            // int lane = Greenfoot.getRandomNumber(laneCount);
            // if (!laneSpawners[lane].isTouchingVehicle()){
                // int vehicleType = Greenfoot.getRandomNumber(4);
                // spawnVehicle();
                // if (vehicleType == 0){
                    // addObject(new Car(laneSpawners[lane]), 0, 0);
                // } else if (vehicleType == 1){
                    // addObject(new Bus(laneSpawners[lane]), 0, 0);
                // } else if (vehicleType == 2){
                    // addObject(new Ambulance(laneSpawners[lane]), 0, 0);
                // } else if (vehicleType == 3) {
                    // addObject(new ExplosiveTruck(laneSpawners[lane]), 0, 0);
                // }
            //}
        }

        // Chance to spawn a Pedestrian
        if (Greenfoot.getRandomNumber (30) == 0){
            // int xSpawnLocation = Greenfoot.getRandomNumber (600) + 100; // random between 99 and 699, so not near edges
            // boolean spawnAtTop = Greenfoot.getRandomNumber(2) == 0 ? true : false;
            // if (spawnAtTop){
                // spawnPedestrian(xSpawnLocation, TOP_SPAWN);
            // } else {
                // spawnPedestrian(xSpawnLocation, BOTTOM_SPAWN);
            // }
            spawnPedestrian();
        }

    }
    /**
     * Spawns a Vehicle.
     */
    private void spawnVehicle() {
        int lane = Greenfoot.getRandomNumber(laneCount);
        VehicleSpawner spawn = laneSpawners[lane];
        if (spawn.isTouchingVehicle()) return;
        int vehicleType = Greenfoot.getRandomNumber(NUM_VEHICLE_TYPES+2); // add 2 to increase chance for variable spawn
        switch(vehicleType){
            case 0:
                addObject(new Car(spawn), 0, 0);
                break;
            case 1:
                addObject(new Bus(spawn), 0, 0);
                break;
            case 2:
                addObject(new Ambulance(spawn), 0, 0);
                break;
            case 3:
                addObject(new ExplosiveTruck(spawn), 0, 0);
                break;
            default: // capture all other possibilities.
                // This is the variable spawn, where what appears will be dependent on the current world state.
                
                ArrayList<Pedestrian> peds = (ArrayList<Pedestrian>)getObjects(Pedestrian.class);
                if (peds.size() > 20) { // Too many people on screen. Add an explosive truck to clear a bit.
                    addObject(new ExplosiveTruck(spawn), 0, 0);
                    break;
                }
                
                // Getting here means there were not enough people. Check if an ambulance is needed.
                peds.removeIf(p -> p.isAwake()); // remove awake pedestrians.
                if (peds.size() > 10) { // Too many downed people and/or zombies. Add an ambulance.
                    addObject(new Ambulance(spawn),0,0);
                    break;
                }
                else { // No need to add anything specific, then.
                    // Randomly choose between adding a Bus or a Car.
                    if (Greenfoot.getRandomNumber(2) == 0) {
                        addObject(new Bus(spawn),0,0);
                    }
                    else addObject(new Car(spawn), 0, 0);
                }
        }
    }
    /**
     * Spawns a Pedestrian.
     */
    private void spawnPedestrian() {
        int xSpawnLocation = Greenfoot.getRandomNumber (600) + 100; // random between 99 and 699, so not near edges
        boolean spawnAtTop = Greenfoot.getRandomNumber(2) == 0 ? true : false;
        int ySpawnLocation = spawnAtTop ? TOP_SPAWN:BOTTOM_SPAWN;
        int direction = spawnAtTop ? 1:-1;
        switch (Greenfoot.getRandomNumber(NUM_PEDESTRIAN_TYPES+2)) { // add 2 to increase chance for variable spawn
            case 0:
                addObject(new Civilian(direction), xSpawnLocation, ySpawnLocation);
                break;
            case 1:
                addObject(new Soldier(direction), xSpawnLocation, ySpawnLocation);
                break;
            case 2:
                addObject(new Medic(direction), xSpawnLocation, ySpawnLocation);
                break;
            case 3:
                addObject(new Zombie(direction), xSpawnLocation, ySpawnLocation);
                break;
            default: // capture any other possibilities.
                // This is the variable spawn. Will react accordingly to current world conditions.
                
                //int numZombies = getObjects(Zombie.class).size();
                int numZombies = pStats[0];
                if (numZombies > 10) { // Too many zombies!
                    addObject(new Soldier (direction), xSpawnLocation, ySpawnLocation);
                    break;
                }
                else if (numZombies < 5 || !daytime) { // Too few Zombies! Or it's currently nighttime...
                    addObject(new Zombie(direction), xSpawnLocation, ySpawnLocation);
                    break;
                }
                
                // Getting here means no need to spawn a new Zombie nor Soldier; check if a Medic is needed.
                ArrayList<Human> downedHumans = (ArrayList<Human>)getObjects(Human.class);
                downedHumans.removeIf(h -> h.isAwake());
                if (downedHumans.size() > 3) {
                    addObject(new Medic(direction), xSpawnLocation, ySpawnLocation);
                }
                else { // Nothing needed. Just add a Civilian, then.
                    addObject(new Civilian(direction), xSpawnLocation, ySpawnLocation);
                }
                break;
        }
    }
    /**
     * Returns the number of lanes that exist.
     * @return The number of lanes that exist in the World.
     */
    public int getNumLanes(){
        return lanePositionsY.length;
    }
    /**
     * Returns the height for the lanes.
     * @return The lane height.
     */
    public int getLaneHeight() {
        return laneHeight;
    }
    /**
     *  Given a lane number (zero-indexed), return the y position
     *  in the centre of the lane. (doesn't factor offset, so 
     *  watch your offset, i.e. with Bus).
     *  
     *  @param lane the lane number (zero-indexed)
     *  @return int the y position of the lane's center, or -1 if invalid
     */
    public int getLaneY (int lane){
        if (lane >= 0 && lane < lanePositionsY.length){
            return lanePositionsY[lane];
        } 
        return -1;
    }

    /**
     * Given a y-position, return the lane number (zero-indexed).
     * Note that the y-position must be valid, and you should 
     * include the offset in your calculations before calling this method.
     * For example, if a Bus is in a lane at y=100, but is offset by -20,
     * it is actually in the lane located at y=80, so you should send
     * 80 to this method, not 100.
     * 
     * @param y - the y position of the lane the Vehicle is in
     * @return int the lane number, zero-indexed
     * 
     */
    public int getLane (int y){
        for (int i = 0; i < lanePositionsY.length; i++){
            if (y == lanePositionsY[i]){
                return i;
            }
        }
        return -1;
    }

    public static int[] prepareLanes (World world, GreenfootImage target, VehicleSpawner[] spawners, int startY, int heightPerLane, int lanes, int spacing, boolean twoWay, boolean centreSplit, int centreSpacing)
    {
        // Declare an array to store the y values as I calculate them
        int[] lanePositions = new int[lanes];
        // Pre-calculate half of the lane height, as this will frequently be used for drawing.
        // To help make it clear, the heightOffset is the distance from the centre of the lane (it's y position)
        // to the outer edge of the lane.
        int heightOffset = heightPerLane / 2;
        // draw top border
        /*
        target.setColor (GREY_BORDER);
        target.fillRect (0, startY, target.getWidth(), spacing);
        */
        // Main Loop to Calculate Positions and draw lanes
        for (int i = 0; i < lanes; i++){
            // calculate the position for the lane
            lanePositions[i] = startY + spacing + (i * (heightPerLane+spacing)) + heightOffset ;
            /*
            // draw lane
            target.setColor(GREY_STREET); 
            // the lane body
            target.fillRect (0, lanePositions[i] - heightOffset, target.getWidth(), heightPerLane);
            // the lane spacing - where the white or yellow lines will get drawn
            target.fillRect(0, lanePositions[i] + heightOffset, target.getWidth(), spacing);
            */
            // Place spawners and draw lines depending on whether its 2 way and centre split
            if (twoWay && centreSplit){
                // first half of the lanes go rightward (no option for left-hand drive, sorry UK students .. ?)
                if ( i < lanes / 2){
                    spawners[i] = new VehicleSpawner(false, heightPerLane, i);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else { // second half of the lanes go leftward
                    spawners[i] = new VehicleSpawner(true, heightPerLane, i);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw yellow lines if middle 
                if (i == lanes / 2){
                    target.setColor(YELLOW_LINE);
                    target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                } else if (i > 0){ // draw white lines if not first lane
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                } 

            } else if (twoWay){ // not center split
                if ( i % 2 == 0){
                    spawners[i] = new VehicleSpawner(false, heightPerLane, i);
                    world.addObject(spawners[i], target.getWidth(), lanePositions[i]);
                } else {
                    spawners[i] = new VehicleSpawner(true, heightPerLane, i);
                    world.addObject(spawners[i], 0, lanePositions[i]);
                }

                // draw Grey Border if between two "Streets"
                if (i > 0){ // but not in first position
                    if (i % 2 == 0){
                        target.setColor(GREY_BORDER);
                        target.fillRect(0, lanePositions[i] - heightOffset - spacing, target.getWidth(), spacing);

                    } else { // draw dotted lines
                        for (int j = 0; j < target.getWidth(); j += 120){
                            target.setColor (YELLOW_LINE);
                            target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                        }
                    } 
                }
            } else { // One way traffic
                spawners[i] = new VehicleSpawner(true, heightPerLane, i);
                world.addObject(spawners[i], 0, lanePositions[i]);
                /*
                if (i > 0){
                    for (int j = 0; j < target.getWidth(); j += 120){
                        target.setColor (Color.WHITE);
                        target.fillRect (j, lanePositions[i] - heightOffset - spacing, 60, spacing);
                    }
                }
                */
            }
        }
        // draws bottom border
        /*
        target.setColor (GREY_BORDER);
        target.fillRect (0, lanePositions[lanes-1] + heightOffset, target.getWidth(), spacing);
        */
        return lanePositions;
    }

    /**
     * A z-sort method which will sort Actors so that Actors that are
     * displayed "higher" on the screen (lower y values) will show up underneath
     * Actors that are drawn "lower" on the screen (higher y values), creating a
     * better perspective. 
     */
    public static void zSort (ArrayList<Actor> actorsToSort, World world){
        ArrayList<ActorContent> acList = new ArrayList<ActorContent>();
        // Create a list of ActorContent objects and populate it with all Actors sent to be sorted
        for (Actor a : actorsToSort){
            acList.add (new ActorContent (a, a.getX(), a.getY()));
        }    
        // Sort the Actor, using the ActorContent comparitor (compares by y coordinate)
        Collections.sort(acList);
        // Replace the Actors from the ActorContent list into the World, inserting them one at a time
        // in the desired paint order (in this case lowest y value first, so objects further down the 
        // screen will appear in "front" of the ones above them).
        for (ActorContent a : acList){
            Actor actor  = a.getActor();
            world.removeObject(actor);
            world.addObject(actor, a.getX(), a.getY());
        }
    }

    /**
     * <p>The prepareLanes method is a static (standalone) method that takes a list of parameters about the desired roadway and then builds it.</p>
     * 
     * <p><b>Note:</b> So far, Centre-split is the only option, regardless of what values you send for that parameters.</p>
     *
     * <p>This method does three things:</p>
     * <ul>
     *  <li> Determines the Y coordinate for each lane (each lane is centered vertically around the position)</li>
     *  <li> Draws lanes onto the GreenfootImage target that is passed in at the specified / calculated positions. 
     *       (Nothing is returned, it just manipulates the object which affects the original).</li>
     *  <li> Places the VehicleSpawners (passed in via the array parameter spawners) into the World (also passed in via parameters).</li>
     * </ul>
     * 
     * <p> After this method is run, there is a visual road as well as the objects needed to spawn Vehicles. Examine the table below for an
     * in-depth description of what the roadway will look like and what each parameter/component represents.</p>
     * 
     * <pre>
     *                  <=== Start Y
     *  ||||||||||||||  <=== Top Border
     *  /------------\
     *  |            |  
     *  |      Y[0]  |  <=== Lane Position (Y) is the middle of the lane
     *  |            |
     *  \------------/
     *  [##] [##] [##| <== spacing ( where the lane lines or borders are )
     *  /------------\
     *  |            |  
     *  |      Y[1]  |
     *  |            |
     *  \------------/
     *  ||||||||||||||  <== Bottom Border
     * </pre>
     * 
     * @param world     The World that the VehicleSpawners will be added to
     * @param target    The GreenfootImage that the lanes will be drawn on, usually but not necessarily the background of the World.
     * @param spawners  An array of VehicleSpawner to be added to the World
     * @param startY    The top Y position where lanes (drawing) should start
     * @param heightPerLane The height of the desired lanes
     * @param lanes     The total number of lanes desired
     * @param spacing   The distance, in pixels, between each lane
     * @param twoWay    Should traffic flow both ways? Leave false for a one-way street (Not Yet Implemented)
     * @param centreSplit   Should the whole road be split in the middle? Or lots of parallel two-way streets? Must also be two-way street (twoWay == true) or else NO EFFECT
     * 
     */
    public static int[] prepareLanes (World world, GreenfootImage target, VehicleSpawner[] spawners, int startY, int heightPerLane, int lanes, int spacing, boolean twoWay, boolean centreSplit){
        return prepareLanes (world, target, spawners, startY, heightPerLane, lanes, spacing, twoWay, centreSplit, spacing);
    }

}

/**
 * Container to hold and Actor and an LOCAL position (so the data isn't lost when the Actor is temporarily
 * removed from the World).
 */
class ActorContent implements Comparable <ActorContent> {
    private Actor actor;
    private int xx, yy;
    public ActorContent(Actor actor, int xx, int yy){
        this.actor = actor;
        this.xx = xx;
        this.yy = yy;
    }

    public void setLocation (int x, int y){
        xx = x;
        yy = y;
    }

    public int getX() {
        return xx;
    }

    public int getY() {
        return yy;
    }

    public Actor getActor(){
        return actor;
    }

    public String toString () {
        return "Actor: " + actor + " at " + xx + ", " + yy;
    }

    public int compareTo (ActorContent a){
        return this.getY() - a.getY();
    }

}
