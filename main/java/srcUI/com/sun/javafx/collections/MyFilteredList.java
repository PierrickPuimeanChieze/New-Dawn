package com.sun.javafx.collections;

import com.sun.javafx.collections.transformation.FilterableList;
import com.sun.javafx.collections.transformation.Matcher;
import com.sun.javafx.collections.transformation.TransformationList;
import java.lang.reflect.Array;
import java.util.*;
import javafx.collections.ListChangeListener;

/**
 * 
 * @author Pierrick Puimean-Chieze
 */
public final class MyFilteredList<E> extends TransformationList<E, E> implements
		FilterableList<E> {

	private FilterableList.FilterMode mode;
	private Matcher<? super E> matcher;
	private int[] filtered;
	private int size;
	private boolean matcherChanged;

	private int findPosition(int paramInt) {
		if (this.filtered.length == 0) {
			return 0;
		}
		int i = Arrays.binarySearch(this.filtered, 0, this.size, paramInt);
		if (i < 0) {
			i ^= -1;
		}
		return i;
	}

	private void ensureSize(int paramInt) {
		if (this.filtered.length < paramInt) {
			int[] arrayOfInt = new int[paramInt * 3 / 2 + 1];
			System.arraycopy(this.filtered, 0, arrayOfInt, 0, this.size);
			this.filtered = arrayOfInt;
		}
	}

	private void removeFilteredRange(int paramInt1, int paramInt2,
			List<? extends E> paramList, List<E> paramList1) {
		int i = paramInt1 + paramList.size();
		int j = findPosition(i);
		int k = j - paramInt2;
		if (paramInt2 != j) {
			for (int m = paramInt2; m < j; m++) {
				paramList1.add(paramList.get(this.filtered[m] - paramInt1));
			}
			System.arraycopy(this.filtered, j, this.filtered, paramInt2,
					this.size - j);
			this.size -= k;
		}
	}

	private void updateIndexes(int paramInt1, int paramInt2) {
		for (int i = paramInt1; i < this.size; i++) {
			this.filtered[i] += paramInt2;
		}
	}

	private void update(int paramInt1, List<? extends E> paramList,
			int[] paramArrayOfInt, int paramInt2) {
		if ((paramInt2 | paramList.size()) == 0) {
			return;
		}
		int i = findPosition(paramInt1);
		ArrayList localArrayList = new ArrayList();
		removeFilteredRange(paramInt1, i, paramList, localArrayList);
		ensureSize(this.size + paramInt2 - localArrayList.size());
		System.arraycopy(this.filtered, i, this.filtered, i + paramInt2,
				this.size - i);
		for (int j = 0; j < paramInt2; j++) {
			this.filtered[(i + j)] = paramArrayOfInt[j];
		}
		this.size += paramInt2;
		updateIndexes(i + paramInt2, paramInt2 - paramList.size());
		fireChange(new NonIterableChange.GenericAddRemoveChange(i, i
				+ paramInt2, Collections.unmodifiableList(localArrayList), this));
	}

	public MyFilteredList(List<E> paramList, Matcher<? super E> paramMatcher,
			FilterableList.FilterMode paramFilterMode) {
		super(paramList);
		if (paramMatcher == null) {
			throw new NullPointerException();
		}
		this.matcher = paramMatcher;
		this.mode = paramFilterMode;
		this.filtered = new int[paramList.size() * 3 / 2 + 1];
		if (paramFilterMode == FilterableList.FilterMode.LIVE) {
			if (!this.observable) {
				throw new IllegalArgumentException(
						"Cannot use LIVE mode with list that is not an ObservableList");
			}
			refilter();
		} else {
			this.size = paramList.size();
			for (int i = 0; i < this.size; i++) {
				this.filtered[i] = i;
			}
		}
	}

	public MyFilteredList(List<E> paramList, Matcher<? super E> paramMatcher) {
		this(paramList, paramMatcher, FilterableList.FilterMode.LIVE);
	}

	protected void onSourceChanged(
			ListChangeListener.Change<? extends E> paramChange) {
		while (paramChange.next()) {

			if (this.mode == FilterableList.FilterMode.BATCH) {
				int[] arrayOfInt1 = new int[paramChange.getAddedSize()];
				for (int j = 0; j < arrayOfInt1.length; j++) {
					arrayOfInt1[j] = (paramChange.getFrom() + j);
				}
				update(paramChange.getFrom(), paramChange.getRemoved(),
						arrayOfInt1, arrayOfInt1.length);
			} else {
				if (paramChange.wasPermutated()) {
					refilter();
				}
				int i = 0;
				int[] arrayOfInt2 = new int[paramChange.getAddedSize()];
				for (int k = paramChange.getFrom(); k < paramChange.getTo(); k++) {
					if (this.matcher.matches(this.source.get(k))) {
						arrayOfInt2[(i++)] = k;
					}
				}
				update(paramChange.getFrom(), paramChange.getRemoved(),
						arrayOfInt2, i);
			}
		}
	}

	public boolean addAll(E[] paramArrayOfE) {
		throw new UnsupportedOperationException();
	}

	public boolean setAll(E[] paramArrayOfE) {
		throw new UnsupportedOperationException();
	}

	public boolean setAll(Collection<? extends E> paramCollection) {
		throw new UnsupportedOperationException();
	}

	public boolean removeAll(E[] paramArrayOfE) {
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(E[] paramArrayOfE) {
		throw new UnsupportedOperationException();
	}

	public void remove(int paramInt1, int paramInt2) {
		throw new UnsupportedOperationException();
	}

	public int size() {
		return this.size;
	}

	public boolean isEmpty() {
		return this.size == 0;
	}

	public boolean contains(Object paramObject) {
		return indexOf(paramObject) != -1;
	}

	public Object[] toArray() {
		Object[] arrayOfObject = new Object[this.size];
		for (int i = 0; i < this.size; i++) {
			arrayOfObject[i] = get(i);
		}
		return arrayOfObject;
	}

	public <T> T[] toArray(T[] paramArrayOfT) {
		Object[] localObject;
		if (paramArrayOfT.length < this.size) {
			localObject = (Object[]) (Object[]) Array.newInstance(paramArrayOfT
					.getClass().getComponentType(), this.size);
		} else {
			localObject = paramArrayOfT;
		}
		for (int i = 0; i < this.size; i++) {
			localObject[i] = get(i);
		}
		return (T[]) (Object[]) localObject;
	}

	public E get(int paramInt) {
		if (paramInt >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		return this.source.get(this.filtered[paramInt]);
	}

	public int indexOf(Object paramObject) {
		for (int i = 0; i < size(); i++) {
			if (((paramObject == null) && (get(i) == null))
					|| ((paramObject != null) && (paramObject.equals(get(i))))) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(Object paramObject) {
		for (int i = size() - 1; i >= 0; i--) {
			if (((paramObject == null) && (get(i) == null))
					|| ((paramObject != null) && (paramObject.equals(get(i))))) {
				return i;
			}
		}
		return -1;
	}

	private void refilter() {
		ensureSize(this.source.size());
		ArrayList localArrayList = new ArrayList(this);
		this.size = 0;
		int i = 0;
		for (Iterator<? extends E> localIterator = this.source.iterator(); localIterator
				.hasNext();) {
			E localObject = localIterator.next();
			if (this.matcher.matches(localObject)) {
				this.filtered[(this.size++)] = i;
			}
			i++;
		}
		fireChange(new NonIterableChange.GenericAddRemoveChange(0, this.size,
				localArrayList, this));
	}

	public void filter() {
		if (this.mode == FilterableList.FilterMode.BATCH) {
			if (this.matcherChanged) {
				refilter();
				this.matcherChanged = false;
				return;
			}

			for (int i = 0; i < this.size; i++) {
				for (int j = i == 0 ? 0 : this.filtered[(i - 1)] + 1; j < this.filtered[i]; j++) {
					if (this.matcher.matches(this.source.get(j))) {
						refilter();
						return;
					}
				}
			}
			for (int i = this.filtered[(this.size - 1)] + 1; i < this.source
					.size(); i++) {
				if (this.matcher.matches(this.source.get(i))) {
					refilter();
					return;
				}
			}

			int i = -1;
			int j = -1;
			ArrayList localArrayList = new ArrayList();
			for (int k = 0; k < this.size; k++) {
				if (!this.matcher.matches(this.source.get(this.filtered[k]))) {
					if (i == -1) {
						i = k;
					}
					this.filtered[k] ^= -1;
					j = k;
				}
			}
			j++;

			if (i == -1) {
				return;
			}

			for (int k = i; k < j; k++) {
				localArrayList
						.add(this.source
								.get(this.filtered[k] < 0 ? this.filtered[k] ^ 0xFFFFFFFF
										: this.filtered[k]));
			}

			for (int k = i; k < j; k++) {
				if (this.filtered[k] < 0) {
					System.arraycopy(this.filtered, k + 1, this.filtered, k,
							this.size - k - 1);
					this.size -= 1;
					j--;
					k--;
				}
			}

			fireChange(new NonIterableChange.GenericAddRemoveChange(i, j,
					Collections.unmodifiableList(localArrayList), this));
		}
	}

	public void setMode(FilterableList.FilterMode paramFilterMode) {
		if (this.mode != paramFilterMode) {
			this.mode = paramFilterMode;
			if (paramFilterMode == FilterableList.FilterMode.LIVE) {
				filter();
			}
		}
	}

	public FilterableList.FilterMode getMode() {
		return this.mode;
	}

	public void setMatcher(Matcher<? super E> paramMatcher) {
		if (this.matcher != paramMatcher) {
			this.matcher = paramMatcher;
			if (this.mode == FilterableList.FilterMode.LIVE) {
				refilter();
			} else {
				this.matcherChanged = true;
			}
		}
	}

	public Matcher<? super E> getMatcher() {
		return this.matcher;
	}

	public int getSourceIndex(int paramInt) {
		if (paramInt >= this.size) {
			throw new IndexOutOfBoundsException();
		}
		return this.filtered[paramInt];
	}
}
