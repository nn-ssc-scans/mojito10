package com.box.l10n.mojito.android.strings;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AndroidPlural extends AbstractAndroidString {

    private final Map<AndroidPluralQuantity, AndroidPluralItem> items;

    public static AndroidPluralBuilder builder() {
        return new AndroidPluralBuilder();
    }

    public AndroidPlural(String name, String comment, List<AndroidPluralItem> items) {
        super(name, comment);
        this.items = items.stream().collect(Collectors.toMap(AndroidPluralItem::getQuantity, Function.identity()));
    }

    public Map<AndroidPluralQuantity, AndroidPluralItem> getItems() {
        return items;
    }

    public void forEachItemSorted(Consumer<AndroidPluralItem> itemConsumer){
        items.values().stream()
                .sorted(Comparator.comparingInt(item -> item.getQuantity().ordinal()))
                .forEach(itemConsumer);
    }

    @Override
    public boolean isSingular() {
        return false;
    }

    public static final class AndroidPluralBuilder {

        private List<AndroidPluralItem> items = new ArrayList<>();
        private String comment;
        private String name;

        public AndroidPluralBuilder() {
        }

        public AndroidPluralBuilder addItem(AndroidPluralItem item) {
            items.add(item);
            return this;
        }

        public AndroidPluralBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public AndroidPluralBuilder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public AndroidPlural build() {
            return new AndroidPlural(name, comment, items);
        }
    }
}