/**
Compilation of Sorting and Searching methods: Merge Sort, Bubble Sort and Binary search.
@author Lucas Paz.
*/
class SortingAndSearching {
	 
	 int[] originalArray;
	 int[] cloneArray;
	 
	 public void mergeSort(int leftIndex, int rightIndex) {
		 
		 if (rightIndex - leftIndex < 1) return;
		 
		 int midIndex = (leftIndex + rightIndex) / 2;
		 
		 if (leftIndex != midIndex) mergeSort(leftIndex, midIndex);
		 if (midIndex + 1 != rightIndex) mergeSort(midIndex + 1, rightIndex);
		 
		 int i = leftIndex, a = leftIndex, b = midIndex + 1;
		 
		 while (a <= midIndex && b <= rightIndex) {
			 
			 if (originalArray[a] <= originalArray[b]) cloneArray[i++] = originalArray[a++];
			 else cloneArray[i++] = originalArray[b++];
			 
		 }
		 
		 while (a <= midIndex) cloneArray[i++] = originalArray[a++];

		 while (b <= rightIndex) cloneArray[i++] = originalArray[b++];
		 
		 for (i = leftIndex; i <= rightIndex; i++) originalArray[i] = cloneArray[i];
		 
	 }
	 
	 public int[] bubbleSort(int[] array) {
		 
		 if (array == null) return null;
		 
		 int n = array.length;
		 
		 for (int leastCompletedIndex = n; leastCompletedIndex > 1; leastCompletedIndex--) {
			 
			 boolean foundUnsortedElements = false;
			 
			 for (int i = 0; i < leastCompletedIndex - 1; i++) {
				 
				 if (array[i] > array[i + 1]) {
					 
					 foundUnsortedElements = true;
					 
					 int temp = array[i];
					 array[i] = array[i + 1];
					 array[i + 1] = temp;
					 
				 }
				 
			 }
			 
			 if ( ! foundUnsortedElements ) return array;
			 
		 }
		 
		 return array;
	 }
	 
	@SuppressWarnings("unchecked")
	public <T> int binarySearch(Comparable<T> element, Comparable<T>[] array) {
		 
		 int indexOfElement = -1;
		 
		 if (array == null) return -1;
		 
		 int n = array.length;
		 
		 if (n < 1) return -1;

		 if (element.compareTo((T) array[0]) < 0) return -1;
		 if (element.compareTo((T) array[0]) == 0) return 0;
		 if (element.compareTo((T) array[n - 1]) == 0) return n - 1;
		 if (element.compareTo((T) array[n - 1]) > 0) return -1;

		 int leftIndex = 0, rightIndex = n - 1;
		 int midIndex = (leftIndex + rightIndex) / 2;
		 
		 do {
			 
			 int comparison = element.compareTo((T) array[midIndex]);
			 
			 if (comparison == 0) return midIndex;
			 else if (comparison > 0) leftIndex = midIndex;
			 else if (comparison < 0) rightIndex = midIndex;
			 
			 midIndex = (leftIndex + rightIndex) / 2;
			 
		 } while (leftIndex != midIndex);
		 
		 if (element.compareTo((T) array[midIndex]) == 0) return midIndex;
		 
		 return indexOfElement;
	 }
}
