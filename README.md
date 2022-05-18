# Elevator System API

# How to run

## IDE
Import project as Maven and run main class. Remember that you have to
give arguments for application to work. Arguments: floorsAmount, elevatorsAmount.

#Information
Application provides app package used to handle manual interaction.
- App is a class with main method to run application. It needs initial arguments for floors amount and elevators amount.
- InstructionParser is a class which parses instruction from command line to be used further.
- InstructionHandler is a class which takes parsed data and handles specified instruction.
- Passenger is used to simulate order instruction.

#API features
ElevatorSystem provides public API for picking up elevator from starting floor and to specified direction,
updating starting floor and target floor of specified idle elevator, ordering which is improved pickup but with target floor instead od direction,
making step in system which moves elevators or change theirs direction and status which provides data about elevators current state.

Elevator class provides public methods for checking current floor and getting id.

ElevatorStatus class provides public method for printing status of elevator.

##Metric
Metric is calculated based on floor to pickup, direction of request, elevator's floor and direction:
- elevator is idle or is heading towards pickup request and has same direction, then metric is the best possible.
- elevator is heading away from pickup request or is heading towards but with different direction, then metric second best possible.
- elevator is busy, which means that it has too many orders compared to other elevators. Metric in this situation is the worst.

##Handling orders
Each elevator has two sets for orders - orders above elevator and orders below elevator.

Elevator is going in one direction until all orders in set corresponding to this direction were handled.
Then elevator is changing direction and is handling orders from second set.
When both sets are empty, elevator becomes idle, with direction set to none.
If elevator is idle then direction is set depending on amount of orders in each set.
When there is more orders above elevator then direction is set upwards, otherwise is set downwards.

#User interface and how to use
Instructions available for user:
- step *numberOfSteps(optional)*
- pickup *floorNumber* *up/down(direction)* 
- update *elevatorId* *currentFloor* *targetFloor*
- order *pickupFloor* *targetFloor*
- exit

Status is printing current status of all elevators as list of status objects.
E*id* - Elevator *id*, CF - current floor, TF - target floor.

Step is making one step in elevator system. Step means moving elevators or changing theirs direction.
After that it prints status of elevators.

Pickup is simulating pressing a button with direction up or down on specified floor.

Update is used to set up idle elevators and it needs id of elevator to set, starting floor and destination floor.

Order is better version od pickup, because instead of direction it needs destination floor.
Firstly it picks up passenger from pickup floor and when elevator reaches this floor it makes order for destination floor.

Exit is used to exit program.