package getumbrellad.models.exception;

public class Player extends Character implements Spawnable{
    private int money;
    
    public Player(String name, LevelGameplayPanelGUI panel, int x, int y, int maxHP, int hp, int damage) {
        super(name, panel, x, y, maxHP, hp, damage);
        this.money = 0;
    }
    
    
    public int getDamage() {
        return this.damage;
    }
    public int getMoney() {
        return this.money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    @Override
    public void spawn(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /* while there is no shop
    public void talkTo(NPC npc) {
        npc.interact();
    }
    
    public void buyFrom(NPC npc) {
        npc.openShop();
    }
    
    public void purchaseItem(Shop shop) {
        
    }
    */
    
    public void buff(String stat, int amount) {
        switch(stat) {
            case "coin":
                this.money += amount;
                break;
            case "health":
                this.hp += amount;
                break;
            default:
                //throwBuffTypeNotFound
        }
    }

}
