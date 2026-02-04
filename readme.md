# DA Java Minecraft Plugin

**This plugin should use Minecraft Version 1.21.4 and you should test on this version of the game**

This contains information on how to set up the code created by the DA Model and how to test it for those doing R&R's. **You need a Minecraft account to be able to do any of this.**

## Compilation

The IDE I use is IntelliJ, and when 'running' the code, I use a Maven run configuration.
If this has ran successfully, two Jar files will be created in the target folder. The folder will not exist until it has been compiled. The Jar file to use will be titled 'MinecraftBackpack-1.jar'.

## Server Creation

To test the code, you will need to create a minecraft server. If you do not have one already, this is how you would create a locally hosted server.

In the server folder, there are 3 files. A Paper.jar file, an Eula.txt file, and a Run.bat file. Extract these 2 files into a folder elsewhere on your PC, and run the .bat file.

**Please verify the contents of the .bat file in a text editor before you open it, so you can ensure it is safe.**

Once this has opened, a command prompt will open giving details of the server loading. Once the server has been created, you will notice many other folders and files. From here, close the command prompt box that has been opened. 

## Server Use
One of these newly generated folders will be titled 'plugins'. Once you see this, copy and paste the 'MinecraftBackpack-1.jar' file into the folder.

Load up the server again using the run.bat file.

Once the server has been created, log onto the server by clicking Multiplayer -> Direct Connection -> and typing 'localhost' as the server IP.

This will spawn you into the new server.

Finally, in the command prompt box, type in 'op [playername]', where you replace [playername] with your Minecraft username. This will make you an operator of the server and allow you to access all the commands. 

## Testing

To test the code, retrieve 5 white wool, 4 string, and 1 crafting table.

This can be achieved by running the following commands:

/give [player] minecraft:white_wool 5

/give [player] minecraft:string 4

/give [player] minecraft:crafting_table 1

Place the crafting table down and open it. Place the wool and the string in alternating slots to give the white bundle.

Right-click the given bundle to open it.


##
Thank you, and happy DA'ing!