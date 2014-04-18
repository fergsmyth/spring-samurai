import java.util.Random
Random rand = new Random()
def probabilityRange = 100

//Initialise each action with a probability of 10/100
def actionProbabilities = [10, 10, 10, 10, 10, 10, 10, 10, 10, 10]

if("${playerState}".equals("LIGHT_ATTACKING") || "${playerState}".equals("CHARGING")){
    //Increase block probability
    actionProbabilities[0] = actionProbabilities[0] + 50
    probabilityRange = probabilityRange + 50
}
else if("${playerState}".equals("HEAVY_ATTACKING") || "${playerState}".equals("CHARGED")){
    //Increase dodge probability
    actionProbabilities[7] = actionProbabilities[7] + 24
    actionProbabilities[8] = actionProbabilities[8] + 24
    probabilityRange = probabilityRange + 24
}

if(distanceToPlayer<1.5f && distanceToPlayer>1.0f){
    //Increase heavy attack probability
    actionProbabilities[6] = actionProbabilities[6] + 50
    probabilityRange = probabilityRange + 50
}
else if(distanceToPlayer<0.3f){
    //Increase light attack probability
    actionProbabilities[5] = actionProbabilities[5] + 50
    probabilityRange = probabilityRange + 50
}
else if(distanceToPlayer>3.0f){
    //Increase walk forward probability
    actionProbabilities[1] = actionProbabilities[1] + 10
    probabilityRange = probabilityRange + 10
    //Decrease walk backward probability
    actionProbabilities[2] = actionProbabilities[2] - 10
    probabilityRange = probabilityRange - 10
}

def randomInteger = rand.nextInt(probabilityRange)
if(randomInteger<actionProbabilities[0]){
    return "BLOCK"
}
else {
    randomInteger= randomInteger - actionProbabilities[0]
}

if(randomInteger<actionProbabilities[1]){
    return "WALK_FORWARD"
}
else {
    randomInteger= randomInteger - actionProbabilities[1]
}

if(randomInteger<actionProbabilities[2]){
    return "WALK_BACKWARDS"
}
else {
    randomInteger= randomInteger - actionProbabilities[2]
}

if(randomInteger<actionProbabilities[3]){
    return "WALK_LEFT"
}
else {
    randomInteger= randomInteger - actionProbabilities[3]
}

if(randomInteger<actionProbabilities[4]){
    return "WALK_RIGHT"
}
else {
    randomInteger= randomInteger - actionProbabilities[4]
}

if(randomInteger<actionProbabilities[5]){
    return "LIGHT_ATTACK"
}
else {
    randomInteger= randomInteger - actionProbabilities[5]
}

if(randomInteger<actionProbabilities[6]){
    return "HEAVY_ATTACK"
}
else {
    randomInteger= randomInteger - actionProbabilities[6]
}

if(randomInteger<actionProbabilities[7]){
    return "DODGE_RIGHT"
}
else {
    randomInteger= randomInteger - actionProbabilities[7]
}

if(randomInteger<actionProbabilities[8]){
    return "DODGE_LEFT"
}
else {
    randomInteger= randomInteger - actionProbabilities[8]
}

if(randomInteger<actionProbabilities[9]){
    return "IDLE"
}


//return "${actionState}"
