package com.newdawn.controllers;

import com.sun.javafx.property.PropertyReference;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.binding.Binding;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.FloatBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.LongBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableFloatValue;
import javafx.beans.value.ObservableIntegerValue;
import javafx.beans.value.ObservableLongValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SelectBinding {

    private static class SelectBindingHelper
            implements InvalidationListener {

        private final Binding<?> binding;
        private final String[] propertyNames;
        private final ObservableValue<?>[] properties;
        private final PropertyReference<?>[] propRefs;
        private final WeakInvalidationListener observer;
        private ObservableList<ObservableValue<?>> dependencies;

        private SelectBindingHelper(Binding<?> paramBinding, ObservableValue<?> paramObservableValue, String[] paramArrayOfString) {
            if (paramObservableValue == null) {
                throw new NullPointerException("Must specify the root");
            }
            if (paramArrayOfString == null) {
                paramArrayOfString = new String[0];
            }

            this.binding = paramBinding;

            int i = paramArrayOfString.length;
            for (int j = 0; j < i; j++) {
                if (paramArrayOfString[j] == null) {
                    throw new NullPointerException("all steps must be specified");
                }
            }

            this.propertyNames = new String[i];
            System.arraycopy(paramArrayOfString, 0, this.propertyNames, 0, i);
            this.properties = new ObservableValue[i + 1];
            this.properties[0] = paramObservableValue;
            this.propRefs = new PropertyReference[i];
            this.observer = new WeakInvalidationListener(this);
            paramObservableValue.addListener(this.observer);
        }

        public void invalidated(Observable paramObservable) {
            this.binding.invalidate();
        }

        private ObservableValue<?> getObservableValue() {
            int i = this.properties.length;
            for (int j = 0; j < i - 1; j++) {
                Object localObject = this.properties[j].getValue();
                try {
                    if ((this.propRefs[j] == null) || (!localObject.getClass().
                            equals(this.propRefs[j].getContainingClass()))) {
                        this.propRefs[j] = new PropertyReference(localObject.
                                getClass(), this.propertyNames[j]);
                    }

                    this.properties[(j + 1)] = this.propRefs[j].getProperty(localObject);
                } catch (RuntimeException localRuntimeException) {
                    updateDependencies();
                    return null;
                }
                this.properties[(j + 1)].addListener(this.observer);
            }
            updateDependencies();
            return this.properties[(i - 1)];
        }

        private void unregisterListener() {
            int i = this.properties.length;
            for (int j = 1; (j < i)
                    && (this.properties[j] != null); j++) {
                this.properties[j].removeListener(this.observer);
                this.properties[j] = null;
            }
            updateDependencies();
        }

        private void updateDependencies() {
            if (this.dependencies != null) {
                this.dependencies.clear();
                int i = this.properties.length;
                for (int j = 0; (j < i)
                        && (this.properties[j] != null); j++) {
                    this.dependencies.add(this.properties[j]);
                }
            }
        }

        public ObservableList<ObservableValue<?>> getDependencies() {
            if (this.dependencies == null) {
                this.dependencies = FXCollections.observableArrayList();
                updateDependencies();
            }

            return FXCollections.unmodifiableObservableList(this.dependencies);
        }
    }

    public static class AsString extends StringBinding {

        private final SelectBinding.SelectBindingHelper helper;

        public AsString(ObservableValue<?> paramObservableValue, String[] paramArrayOfString) {
            this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString);
        }

        public void dispose() {
            this.helper.unregisterListener();
        }

        protected void onInvalidating() {
            this.helper.unregisterListener();
        }

        protected String computeValue() {
            try {
                return (String) this.helper.getObservableValue().getValue();
            } catch (RuntimeException localRuntimeException) {
            }
            return null;
        }

        public ObservableList<ObservableValue<?>> getDependencies() {
            return this.helper.getDependencies();
        }
    }

    public static class AsLong extends LongBinding {

        private static final long DEFAULT_VALUE = 0L;
        private final SelectBinding.SelectBindingHelper helper;

        public AsLong(ObservableValue<?> paramObservableValue, String[] paramArrayOfString) {
            this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString);
        }

        public void dispose() {
            this.helper.unregisterListener();
        }

        protected void onInvalidating() {
            this.helper.unregisterListener();
        }

        protected long computeValue() {
            ObservableValue localObservableValue = this.helper.
                    getObservableValue();
            if (localObservableValue == null) {
                return 0L;
            }
            try {
                return ((ObservableLongValue) localObservableValue).get();
            } catch (ClassCastException localClassCastException2) {
                try {
                    return ((Number) localObservableValue.getValue()).longValue();
                } catch (ClassCastException localClassCastException3) {
                }
            }
            return 0L;
        }

        public ObservableList<ObservableValue<?>> getDependencies() {
            return this.helper.getDependencies();
        }
    }

    public static class AsInteger extends IntegerBinding {

        private static final int DEFAULT_VALUE = 0;
        private final SelectBinding.SelectBindingHelper helper;

        public AsInteger(ObservableValue<?> paramObservableValue, String[] paramArrayOfString) {
            this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString);
        }

        public void dispose() {
            this.helper.unregisterListener();
        }

        protected void onInvalidating() {
            this.helper.unregisterListener();
        }

        protected int computeValue() {
            ObservableValue localObservableValue = this.helper.
                    getObservableValue();
            if (localObservableValue == null) {
                return 0;
            }
            try {
                return ((ObservableIntegerValue) localObservableValue).get();
            } catch (ClassCastException localClassCastException2) {
                try {
                    return ((Number) localObservableValue.getValue()).intValue();
                } catch (ClassCastException localClassCastException3) {
                }
            }
            return 0;
        }

        public ObservableList<ObservableValue<?>> getDependencies() {
            return this.helper.getDependencies();
        }
    }

    public static class AsFloat extends FloatBinding {

        private static final float DEFAULT_VALUE = 0.0F;
        private final SelectBinding.SelectBindingHelper helper;

        public AsFloat(ObservableValue<?> paramObservableValue, String[] paramArrayOfString) {
            this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString);
        }

        public void dispose() {
            this.helper.unregisterListener();
        }

        protected void onInvalidating() {
            this.helper.unregisterListener();
        }

        protected float computeValue() {
            ObservableValue localObservableValue = this.helper.
                    getObservableValue();
            if (localObservableValue == null) {
                return 0.0F;
            }
            try {
                return ((ObservableFloatValue) localObservableValue).get();
            } catch (ClassCastException localClassCastException2) {
                try {
                    return ((Number) localObservableValue.getValue()).floatValue();
                } catch (ClassCastException localClassCastException3) {
                }
            }
            return 0.0F;
        }

        public ObservableList<ObservableValue<?>> getDependencies() {
            return this.helper.getDependencies();
        }
    }

    public static class AsDouble extends DoubleBinding {

        private static final double DEFAULT_VALUE = 0.0D;
        private final SelectBinding.SelectBindingHelper helper;

        public AsDouble(ObservableValue<?> paramObservableValue, String[] paramArrayOfString) {
            this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString);
        }

        public void dispose() {
            this.helper.unregisterListener();
        }

        protected void onInvalidating() {
            this.helper.unregisterListener();
        }

        protected double computeValue() {
            ObservableValue localObservableValue = this.helper.
                    getObservableValue();
            if (localObservableValue == null) {
                return 0.0D;
            }
            try {
                return ((ObservableDoubleValue) localObservableValue).get();
            } catch (ClassCastException localClassCastException2) {
                try {
                    return ((Number) localObservableValue.getValue()).
                            doubleValue();
                } catch (ClassCastException localClassCastException3) {
                }
            }
            return 0.0D;
        }

        public ObservableList<ObservableValue<?>> getDependencies() {
            return this.helper.getDependencies();
        }
    }

    public static class AsBoolean extends BooleanBinding {

        private static final boolean DEFAULT_VALUE = false;
        private final SelectBinding.SelectBindingHelper helper;

        public AsBoolean(ObservableValue<?> paramObservableValue, String[] paramArrayOfString) {
            this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString);
        }

        public void dispose() {
            this.helper.unregisterListener();
        }

        protected void onInvalidating() {
            this.helper.unregisterListener();
        }

        protected boolean computeValue() {
            ObservableValue localObservableValue = this.helper.
                    getObservableValue();
            if (localObservableValue == null) {
                return false;
            }
            try {
                return ((ObservableBooleanValue) localObservableValue).get();
            } catch (ClassCastException localClassCastException2) {
                try {
                    return ((Boolean) localObservableValue.getValue()).
                            booleanValue();
                } catch (ClassCastException localClassCastException3) {
                }
            }
            return false;
        }

        public ObservableList<ObservableValue<?>> getDependencies() {
            return this.helper.getDependencies();
        }
    }

    public static class AsObject<T> extends ObjectBinding<T> {

        private final SelectBinding.SelectBindingHelper helper;

        public AsObject(ObservableValue<?> paramObservableValue, String... paramArrayOfString) {
            this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString);
        }

        public void dispose() {
            this.helper.unregisterListener();
        }

        protected void onInvalidating() {
            this.helper.unregisterListener();
        }

        protected T computeValue() {
            try {
                return (T) this.helper.getObservableValue().getValue();
            } catch (RuntimeException localRuntimeException) {
            }
            return null;
        }

        public ObservableList<ObservableValue<?>> getDependencies() {
            return this.helper.getDependencies();
        }
    }
}