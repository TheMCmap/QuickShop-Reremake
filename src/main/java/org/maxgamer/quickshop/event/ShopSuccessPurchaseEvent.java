/*
 * This file is a part of project QuickShop, the name is ShopSuccessPurchaseEvent.java
 *  Copyright (C) PotatoCraft Studio and contributors
 *
 *  This program is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the
 *  Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 *  for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.maxgamer.quickshop.event;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.maxgamer.quickshop.shop.Shop;

import java.util.UUID;

public class ShopSuccessPurchaseEvent extends QSEvent implements Cancellable {

    @Getter
    @NotNull
    private final Shop shop;

    @Getter
    private final int amount;

    @Getter
    @NotNull
    private final UUID purchaser;

    @Getter
    @Nullable
    @Deprecated
    private final Player player;

    @Getter
    @NotNull
    private final Inventory purchaserInventory;

    @Getter
    private final double tax;

    private final double
            total; // Don't use getter, we have important notice need told dev in javadoc.

    private boolean cancelled;

    /**
     * Builds a new shop purchase event
     * Will called when purchase ended
     *
     * @param shop               The shop bought from
     * @param purchaser          The player buying, may offline if purchase by plugin
     * @param purchaserInventory The purchaseing target inventory, *MAY NOT A PLAYER INVENTORY IF PLUGIN PURCHASE THIS*
     * @param amount             The amount they're buying
     * @param tax                The tax in this purchase
     * @param total              The money in this purchase
     */
    public ShopSuccessPurchaseEvent(
            @NotNull Shop shop, @NotNull UUID purchaser, @NotNull Inventory purchaserInventory, int amount, double total, double tax) {
        this.shop = shop;
        this.purchaser = purchaser;
        this.purchaserInventory = purchaserInventory;
        this.amount = amount * shop.getItem().getAmount();
        this.tax = tax;
        this.total = total;
        this.player = Bukkit.getPlayer(purchaser);
    }

    /**
     * The total money changes in this purchase. Calculate tax, if you want get total without tax,
     * please use getBalanceWithoutTax()
     *
     * @return the total money with calculate tax
     */
    public double getBalance() {
        return this.total * (1 - tax);
    }

    /**
     * The total money changes in this purchase. No calculate tax, if you want get total with tax,
     * please use getBalance()
     *
     * @return the total money without calculate tax
     */
    public double getBalanceWithoutTax() {
        return this.total;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
