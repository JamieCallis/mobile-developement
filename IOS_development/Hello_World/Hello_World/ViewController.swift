//
//  ViewController.swift
//  Hello_World
//
//  Created by Callis J (FCES) on 14/01/2019.
//  Copyright © 2019 Callis J (FCES). All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    @IBOutlet weak var output: UILabel!
    @IBOutlet weak var inputTextFeild: UITextField!
    
    @IBAction func clickMeButton(_ sender: UIButton) {
        if(inputTextFeild.text != "") {
            output.text = "Hello, " + inputTextFeild.text!
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

}

