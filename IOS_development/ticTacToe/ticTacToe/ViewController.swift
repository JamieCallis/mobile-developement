//
//  ViewController.swift
//  ticTacToe
//
//  Created by Callis J (FCES) on 15/01/2019.
//  Copyright © 2019 Callis J (FCES). All rights reserved.
//

import UIKit

class ViewController: UIViewController {
   
    var turn = 1; // 1 is nought, 2 is cross
   
    @IBAction func checkAndNoughtButtonHandler(_ sender: AnyObject) {
        if(turn == 1) {
            sender.setImage(UIImage(named:"cross.png"), for:UIControl.State())
        } else {
            sender.setImage(UIImage(named:"cross.png"), for:UIControl.State())
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

