# User Story

## Launching the Game

1. As a player, I want to launch the game and access the main menu, so that I can choose to start, continue, or configure my game
   1. acceptance criteria: the game launches and the main menu is showed
2. As a player, I want to enable or disable auto-save, so that I can control how my progress is saved
   1. acceptance criteria: see user story
3. As a player, I want to start a new game or continue from a previous save, so that I can play
   1. acceptance criteria: the game starts from the beginning or loads the previous saved state
Start the game
4. As a player, I want to choose my character’s job, so that I can custom the gameplay
   1. acceptance criteria: the system displays available jobs with descriptions.
5. As a player, I want to select a save slot and continue from where I left off, so that I don’t lose my previous progress.
   1. acceptance criteria: the game correctly restarts from the saved point and assigns the right equipment to each character
6. As a player, I want to be notified if a save file is corrupted, so that I know I can’t load.
   1. acceptance criteria: the game correctly detects that the save file is corrupted, shows the error message and doesn’t allow the player to use that slot until it gets overwritten

## Ending the Game

7. As a player, I want to terminate the game before the end of the story, so that i can close the game and save my progress.
   1. acceptance criteria: the game saves progress before exiting and the program closes safely and returns to desktop.
8. As a player, I want to terminate the game at the end of the story, so that I can choose to start over or close the program.
   1. acceptance criteria: the game saves the end of story state and goes to the main menu
9. As a player I want to save my progress in either the current slot or another one so that I can continue my game from the last save
   1. acceptance criteria: the save menu lists all slots and if a slot is already used, confirmation for overwrite is required.
10. As a player, I want to make choices that influence the main story, so that I can experience a unique and personalized narrative.
    1. acceptance criteria: the system records choices for future consequences.

## Actual Gameplay

11. As a player I want to open my inventory so that i can give items to my character
    1. Acceptance criteria:
       1. the game shows the inventory gui
       2. the player can equip his own character with the proper item for a certain slot
12. As a player, I want to interact with NPCs, so that I can buy items or trigger some event
    1. acceptance criteria:
       1. When interacting with NPCs the game temporarily stops its other functions until the player is done with the NPC
       2. New items appear in the player’s inventory
       3. the player can take part in the event
13. As a player, I want to control the main character during exploration, so that the rest of the party follows my movements.
    1. acceptance criteria: the game allows the main character to move around the map, with the rest of the party following behind

## Fighting Enemies

14. As a player, I want to fight the enemies so that I can go on with the plot
    1. acceptance criteria: the game allows the player to choose an attack or use an item
15. As a player, I want the system to calculate damage, effects and healing so that I can see the results of each turn.
    1. acceptance criteria: the game calculates correctly the new combat state
    2. the game properly shows the new combat state
16. As a player, I want battles to proceed in alternating turns until one side is defeated so that the fight resolves fairly.
    1. acceptance criteria: turns alternate between player and enemy and rewards (XP, items) are assigned after victory.
Learning New Abilities
17. As a player, I want to select which new ability to learn so that I can improve my character's skills.
    1. acceptance criteria: when a new ability is available, the system notifies the player.
18. As a player, I want to evolve my character, so that I progress in the game.
    1. acceptance criteria: the new ability appears in the ability list
19. As a player, I want the system to update my character data after learning a new ability, so that it is immediately usable.
    1. acceptance criteria: the system saves the update character and shows visual feedback that the ability has been successfully added.
