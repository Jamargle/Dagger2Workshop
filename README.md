# Dagger2Workshop
This is a simple Dagger2 workshop with the application from the Google's testing codelab 

## INVERSION OF CONTROL
In traditional programming, custom code calls to libraries (or a framework) to perform generic tasks.
In **Inversion of Control**, it is the framework who calls into the custom, or task-specific, code.

Separate *what to do* from *when to do*, and ensures that *when to do* knows as little as possible about *what to do*, and viceversa.

For example:

(Event handling) 
- *When to do* -> Raising events
- *What to do* -> Event handlers

(Interfaces) 
- *When to do* -> Interface in a component client
- *What to do* -> Implementation


## DEPENDENCY INJECTION
**Dependency Injection** is a software design pattern that implements *Inversion of Control* for resolving dependencies. 
A dependency is an object that can be used (a service). 
An injection is the passing of a dependency to a dependent object (a client) that would use it. 
To pass the service to the client, rather than allowing a client to build or find the service, is the fundamental requirement of the Dependency Injection pattern.
The client delegates to external code (the injector) the responsibility of providing its dependencies. It is the injector who constructs the services and calls the client to inject them.

**Dependency Injection** means giving an object its instance variables. This separates the responsibilities of use and construction. 
This means:
- The client code does not need to know about the injected code. 
- The client does not need to know how to construct the services. 
- The client does not need to know which actual services it is using. 
- The client only needs to know about the intrinsic interfaces of the services because these define how the client may use the services. 


## ADVANTAGES of Dependency Injection
- Separates the responsibilities of use and construction -> Isolate the client from the impact of design changes and defects (reusability, testability and maintainability)
- Configurable Client. Only the client's behavior is fixed
- Separate configurations can be written -> for different situations that require different implementations of components. This includes testing.
- Clients more independent -> Easier to unit test in isolation using stubs or mock objects that simulate other objects not under test
- Boilerplate Code (code that have to be included in many places with little or no alteration)
- Concurrent or independent development -> Two developers only needing to know the interface 
- Decreases coupling between a class and its dependencies. 

## DISADVANTAGES of Dependency Injection
More Complexity

- Object construction can be heavy even when obvious defaults are available -> Dependency injection creates clients that demand configuration details be supplied by construction code. 
- Can make code difficult to trace -> it separates behavior from construction. This means developers must refer to more files to follow how a system performs.
- Requires more upfront development effort -> one can not summon into being something right when and where it is needed but must ask that it be injected and then ensure that it has been injected.
- Forces complexity to move out of classes -> into the linkages between classes which might not always be desirable or easily managed
- Can encourage dependence on a dependency injection framework

## Ways to implement Dependency Injection
There are three ways to implement Dependency Injection:
- by constructor. To inject the dependency through the constructor of the client.
- by setter. To inject the dependency by using a method/property of the class.
- by interface. To inject through a method, from an interface that the client implements, receiving as parameter the object to be injected.

## Basic Coffee Maker example

### Without Dependency Injection
*CoffeeMaker* depends on *Heater* and *Pump* because it needs to know how to create them
```bash
public class CoffeeMaker {
  private final Heater heater;
  private final Pump pump;
  
  public CoffeeMaker() {
    this.heater = new ElectricHeater();
    this.pump = new Thermosiphon(heater);
  }
  
  public Coffee makeCoffee() {
    return new Coffee(pump);
  }
}


Coffee cofee = new CoffeeMaker().makeCoffee();
```
- What happens if *ElectricHeater* needs to know more data (Brand, power, voltage) -> *Coffeemaker* needs to know it?? Why??
- What happens if tomorrow there is no more *ElectricHeater*, only *GasHeater* -> You have to change *Coffeemaker* implementation?? Why??
- If you create a *Kettle* for make some tea, it needs to know how to make a *ElectricHeater* and a *Thermosiphon* too?? If tomorrow this change, you change *Coffeemaker* and *Kettle* implementation??

### With Dependency Injection (by constructor)
*CoffeeMaker* does not depend on *Heater* and *Pump* because we provide instances of *Heater* and *Pump* with **Dependency Injection**
```bash
public class CoffeeMaker {
  private final Heater heater;
  private final Pump pump;
  
  public CoffeeMaker(final Heater heater, final Pump pump) {
    this.heater = heater;
    this.pump = pump;
  }
  
  public Coffee makeCoffee() {
    return new Coffee(pump);
  }
}


Heater heater = new ElectricHeater();
Pump pump = new Thermosiphon(heater);
Coffee cofee = new CoffeeMaker(heater, pump).makeCoffee();
```
