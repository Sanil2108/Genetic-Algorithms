def create_random_arrays(array):
    N = 9
    arrays = [[0]] * N
    for i in range(N):
        arrays[i] = create_random_array(array)
    return arrays

def create_random_array(array):
    arr=array[:]
    n = int(len(arr))
    for i in range(int(n / 2)):
        a = random.randint(0, n-1)
        b = random.randint(0, n-1)
        temp = arr[a]
        arr[a] = arr[b]
        arr[b] = temp
    return arr

def fitness(array):
    fitness=0
    for i in range(0, len(array)-1, 1):
        if(array[i]<array[i+1]):
            fitness+=1
    return fitness

def genetic_algo(array):
    #Create a bunch of arrays
    arrays=create_random_arrays(array)
    #Find the fitness array
    fitness_array=[0]*len(arrays)
    #Combine them together to form a fitness array
    for i in range(len(fitness_array)):
        fitness_array[i]=fitness(arrays[i])
    arrays_and_fitness=list(zip(arrays, fitness_array))
    #Sort them
    sorted_arrays_and_fitness=sort_arrays_based_on_fitness(array, arrays_and_fitness)
    #Take the top 2/3 of these 
    n=(2*len(sorted_arrays_and_fitness))/3
    n=int(n)
    print(n)
    sorted_arrays_and_fitness=sorted_arrays_and_fitness[0:n]
    #Create child arrays
    for i in range(0, n, 2):
        child=create_child_array(sorted_arrays_and_fitness[i][0], sorted_arrays_and_fitness[i+1][0])
        child_fitness=fitness(child)
        sorted_arrays_and_fitness.append((child, child_fitness))
    return sorted_arrays_and_fitness

def sort_arrays_based_on_fitness(array, arrays_and_fitness):
    max_fitness = len(array) - 1
    rank_array = [[]] * (max_fitness+1)
    sorted_arrays_and_fitness = [-1] * len(arrays_and_fitness)
    for i in range(len(arrays_and_fitness)):
        temp=arrays_and_fitness[i][0]
        temp2=rank_array[arrays_and_fitness[i][1]][:]
        temp2.append(temp)
        rank_array[arrays_and_fitness[i][1]]=temp2
    j = 0
    for i in range(len(rank_array)-1, 0, -1):
        for k in range(len(rank_array[i])):
            sorted_arrays_and_fitness[j]=(rank_array[i][k], i)
            j+=1
    return sorted_arrays_and_fitness

def create_child_array(array1, array2):
    max_element = array1[0]
    for i in range(1, len(array1), 1):
        if (max_element < array1[i]):
            max_element = array1[i]
    rank_array = [0] * (max_element + 1)
    child_array = [0] * len(array1)
    for i in range(len(array1)):
        rank_array[array1[i]] += i
        rank_array[array2[i]] += i
    rank_array_max_element=rank_array[0]
    for i in range(1, len(rank_array), 1):
        if(rank_array_max_element<rank_array[i]):
            rank_array_max_element=rank_array[i]
    child_rank_array = [-1] * (rank_array_max_element+1)
    for i in range(len(rank_array)):
        child_rank_array[rank_array[i]] = i
        
    j=0
    for i in range(len(child_rank_array)):
        if(child_rank_array[i]!=-1):
            child_array[j]=child_rank_array[i]
            j+=1
    return child_array