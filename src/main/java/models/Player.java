package main.java.models;

import main.java.commands.Command;
import main.java.orders.Order;
import main.java.utils.OrderParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is responsible for the addition and removal of players.
 *
 * @author Angad
 * @version 1.0
 */
public class Player {
    /**
     * list of countries owned by this user
     */
    private List<Country> d_ownedCountries;

    private String d_name;

    private int d_assignedArmyUnits = 0;

    private List<Order> d_ordersList = new ArrayList<>();

    private List<SpecialCard> specialCards = new ArrayList<SpecialCard>(0);

    private boolean isConqueror = false;

    private boolean didCommitForThisTurn = false;

    public Player() {
        d_ownedCountries = new ArrayList<>();
    }

    public Player(String p_inputName) {
        this();
        d_name = p_inputName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Player player)) {
            return false;
        }
        return getD_name().equalsIgnoreCase(player.getD_name());
    }

    /**
     * Getter for name of player
     *
     * @return name of player
     */
    public String getD_name() {
        return d_name;
    }

    /**
     * Setter for name
     *
     * @param p_name name to set
     */
    public void setD_name(String p_name) {
        d_name = p_name;
    }

    /**
     * Getter for ownedCountries
     *
     * @return list of countries
     */
    public List<Country> getD_ownedCountries() {
        return d_ownedCountries;
    }

    /**
     * Setter for ownedCountries
     *
     * @param p_ownedCountries list of countries to set
     */
    public void setD_ownedCountries(List<Country> p_ownedCountries) {
        this.d_ownedCountries = p_ownedCountries;
    }

    /**
     * Retrieve the number of available country units
     * @return available units at this point
     */
    public int getD_assignedArmyUnits() {
        return d_assignedArmyUnits;
    }

    /**
     * Assign a unit of army to this player
     * @param d_assignedArmyUnits units of army to assign
     */
    public void setD_assignedArmyUnits(int d_assignedArmyUnits) {
        System.out.println(getD_name() + " has been assigned " + d_assignedArmyUnits + " reinforcements");
        this.d_assignedArmyUnits = d_assignedArmyUnits;
    }

    /**
     * identifies whether this player is Conqueror for this turn
     * @return true if the player conquered at least one Country in their turn
     */
    public boolean isConqueror() {
        return isConqueror;
    }

    /**
     * set 'Conqueror' flag for this player in this turn
     * @param p_conqueror conqueror flag value
     */
    public void setConqueror(boolean p_conqueror) {
        isConqueror = p_conqueror;
    }

    /**
     * Determines if the player has committed for this turn
     * @return true if player committed after issuing all orders
     */
    public boolean didCommitForThisTurn() {
        return didCommitForThisTurn;
    }

    /**
     * Set the didCommitForThisTurn flag
     *
     * @param didCommitForThisTurn true when player commits after deciding all issuing his command
     */
    public void setCommitForThisTurn(boolean didCommitForThisTurn) {
        this.didCommitForThisTurn = didCommitForThisTurn;
    }

    /**
     * Fetch all the owned Special cards
     * @return list of cards owned by this player
     */
    public List<SpecialCard> getSpecialCards() {
        return specialCards;
    }

    /**
     * Adds a special card to this player's list
     * @param p_specialCard Card to be added to player's cards list
     */
    public void addSpecialCards(SpecialCard p_specialCard) {
        System.out.println(this.getD_name() + " : has been assigned a card: " + p_specialCard);
        this.specialCards.add(p_specialCard);
    }

    /**
     * Identifies if this player has a special card of a given type
     * @param p_cardType type of special card to check
     * @return true if the player has this special card, false otherwise
     */
    public boolean hasSpecialCardOfType(String p_cardType) {
        SpecialCard specialCard = SpecialCard.from(p_cardType);
        if (specialCard != null) {
            return this.hasSpecialCard(specialCard);
        }
        return false;
    }

    /**
     * Identifies whether the player has special card of this type
     * @param p_card special card to check
     * @return true if the player has the special card, false otherwise
     */
    public boolean hasSpecialCard(SpecialCard p_card) {
        return this.specialCards.contains(p_card);
    }

    /**
     * Removes first occurrence of
     * @param p_specialCard card to remove
     * @return true if a special card is removed successfully, false otherwise
     */
    public boolean removeSpecialCard(SpecialCard p_specialCard) {
        return this.specialCards.remove(p_specialCard);
    }

    /**
     * Reduces given number of army units from player's available units
     *
     * @param p_units units to reduce
     */
    public void reduceArmyUnits(int p_units) {
        this.d_assignedArmyUnits -= p_units;
    }

    /**
     * Assign ownership of country to this player
     *
     * @param p_country country to assign
     * @return success or failure
     */
    public boolean receiveOwnershipForCountry(Country p_country) {
        p_country.setD_ownedBy(this);
        return this.d_ownedCountries.add(p_country);
    }

    /**
     * Removes a country from owned countries list

     * @param p_country country to remove
     * @return true if successful, false otherwise
     */
    public boolean removeOwnershipForCountry(Country p_country) {
        return this.d_ownedCountries.remove(p_country);
    }

    /**
     * Determines if this player owns a country  with the given name
     *
     * @param p_countryName name of country to check
     * @return true if player has ownership of country with passed in name, false otherwise
     */
    public boolean hasOwnershipForCountryWithName(String p_countryName) {
        for (Country l_ownedCountry: getD_ownedCountries()) {
            if (l_ownedCountry.getD_countryName().equalsIgnoreCase(p_countryName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a country from owned country list having a given name
     *
     * @param p_countryName name of country to fetch
     * @return Country with passed in name, null otherwise
     */
    // public Country getOwnedCountryWithName(String p_countryName) {
    //     Country l_ownedCountry = null;
    //     for (Country l_country: getD_ownedCountries()) {
    //         if (l_country.getD_countryName().equalsIgnoreCase(p_countryName)) {
    //             l_ownedCountry = l_country;
    //             break;
    //         }
    //     }
    //     return  l_ownedCountry;
    // }

    public Country getOwnedCountryWithName(String p_countryName) {
        Country l_ownedCountry = null;
        for (Country l_country : getD_ownedCountries()) {
            if (l_country.getD_countryName().equalsIgnoreCase(p_countryName)) {
                return l_country;
            }
        }
        return l_ownedCountry;
    }
    

    /**
     * Adds an order to the orders list
     *
     * @param p_order order to append
     */
    public void appendOrderToList(Order p_order) {
        this.d_ordersList.add(p_order);
    }

    /**
     * Fetches the next order from the orders list and removes it from list
     *
     * @return the next available order, null if there are none
     */
    public Order nextorder() {
        try {
            return this.d_ordersList.remove(0);
        } catch (IndexOutOfBoundsException exception) {
            return null;
        }
    }
    /**
     * Ask the player to issue orders, will add an order object to its 'order' list
     */
    public void issue_order() {
        System.out.println("Player [" + getD_name() + "] needs to issue order: ");
        Scanner inputReader = new Scanner(System.in);
        String l_nextOrder = inputReader.nextLine();
        Command l_command = OrderParser.parseOrderStatement(this, l_nextOrder);
        if (l_command != null) {
            l_command.execute();
        } else {
            System.out.println("Valid commands in state [Attack] are: ");
            System.out.println("1. deploy countryID numarmies");
            System.out.println("2. advance countrynamefrom countynameto numarmies");
            System.out.println("3. bomb countryID");
            System.out.println("4. blockade countryID");
            System.out.println("5. airlift sourcecountryID targetcountryID numarmies");
            System.out.println("6. negotiate playerID");
            System.out.println("7. quit");
            System.out.println(" --- ");
        }
    }

    /**
     * Determines if the user can assign any more orders
     *
     * @return true if player can issue orders
     */
    public boolean canIssueOrder() {
        return !didCommitForThisTurn;
    }

    /**
     * Determines if the user can deploy any more troops
     *
     * @return true if player has troops to deploy, false otherwise
     */
    public boolean canDeployTroops() {
        return getD_assignedArmyUnits() > 0;
    }
    /**
     * Randomly assigns a special card to the player
     */
    public void assignRandomCard() {
        Object[] cards = SpecialCard.validValues();
        Random randomNum = new Random();
        int randomCardIndex = randomNum.nextInt(cards.length);
        addSpecialCards((SpecialCard) cards[randomCardIndex]);

        // Once a card has been assigned, mark conqueror as false to avoid adding multiple cards by mistake
        setConqueror(false);
    }
}
